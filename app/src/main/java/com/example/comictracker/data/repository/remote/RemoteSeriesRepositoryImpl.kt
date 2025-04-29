package com.example.comictracker.data.repository.remote

import android.util.Log
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.seriesDTO.Results
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteSeriesRepositoryImpl @Inject constructor(private val api: MarvelComicApi): RemoteSeriesRepository {

    //Маппер
    private fun Results.toModel():SeriesModel{
        return SeriesModel(
            seriesId = this.id!!.toInt(),
            title = this.title!!,
            date = "${this.startYear} - ${this.endYear}",
            desc = this.description,
            image = "${this.thumbnail?.path}.${this.thumbnail?.extension}",
            comics = this.comics!!.items.mapNotNull {
                it.resourceURI?.substringAfter("comics/")?.toIntOrNull()
            },
            creators = this.creators!!.items.map {
                Pair(
                    it.resourceURI!!.substringAfter("creators/").toInt(),
                    it.role!!)
            },
            characters = this.characters!!.items.mapNotNull {
                it.resourceURI?.substringAfter("characters/")?.toIntOrNull()
            },
            connectedSeries = listOf(
                this.next?.resourceURI?.substringAfter("series/")?.toIntOrNull(),
                this.previous?.resourceURI?.substringAfter("series/")?.toIntOrNull(),),
            readMark = "",favoriteMark = false
        )
    }

    override suspend fun getCharacterSeries(characterId: Int, offset: Int): List<SeriesModel> {
        val characterSeries = mutableListOf<SeriesModel>()
        Log.i("Repository","Start get Character sereies")

        val res = api.getCharacterSeries(characterId.toString(), offset = offset.toString()).data!!.results
        res.forEach {
                result -> characterSeries.add(result.toModel())
        }
        Log.i("Repository","gоt Character sereies: $characterSeries")
        return characterSeries
    }

    override suspend fun getAllSeries(loadedCount: Int): List<SeriesModel> {
        val allSeries = mutableListOf<SeriesModel>()
        api.getAllSeries(offset = loadedCount.toString()).data?.results?.forEach {
                result -> allSeries.add(result.toModel())
        }
        return allSeries
    }

    override suspend fun getSeriesByTitle(title: String): List<SeriesModel> {
        val series = mutableListOf<SeriesModel>()
        api.getSeriesByTitle(title).data?.results?.forEach {
                results ->   series.add(results.toModel())
        }
        return series
    }

    override suspend fun getSeriesById(id: Int): SeriesModel {
        Log.i("Repository","Start get sereies")
        val result = api.getSeriesById(id.toString()).data!!.results[0]
        Log.i("Repository","Series got")
        val convertedRes = result.toModel()
        Log.i("Repository","Coverted $convertedRes")
        return convertedRes
    }

    override suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): List<SeriesModel> {
        val series = mutableListOf<SeriesModel>()
        Log.i("Repository","Start get connected sereies ")
        connectedSeriesId.filterNotNull().forEach {
            series.add(api.getSeriesById(it.toString()).data!!.results[0].toModel())
        }
        Log.i("Repository","Start get connected got ")
        return series
    }

    override suspend fun loadMayLikeSeriesIds(loadedIdsSeriesFromBD: List<Int>): List<Int> {
        val mayLikeSeries = mutableListOf<Int>()
        val series = coroutineScope {
            loadedIdsSeriesFromBD.map { id ->
                async {
                    getSeriesById(id)
                }
            }
        }.awaitAll()

        series.forEach {
            val connected = it.connectedSeries.filterNotNull()
            mayLikeSeries.addAll(connected)
        }
        return mayLikeSeries
    }

    override suspend fun fetchSeries(ids: List<Int>): List<SeriesModel> {
        val seriesDef = ids.map { id ->
            withContext(Dispatchers.IO){
                async {
                    try {
                        getSeriesById(id)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
        }
        return seriesDef.awaitAll().filterNotNull()
    }
}