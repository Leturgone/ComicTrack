package com.example.comictracker.data.repository.remote

import android.util.Log
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.seriesDTO.Results
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


    override suspend fun getCharacterSeries(characterId: Int, offset: Int): Result<List<SeriesModel>> {
        return try {
            val characterSeries = mutableListOf<SeriesModel>()
            Log.i("getCharacterSeries","Start get Character series")
            val res = api.getCharacterSeries(characterId.toString(), offset = offset.toString()).data!!.results
            res.forEach {
                    result -> characterSeries.add(result.toModel())
            }
            Log.i("getCharacterSeries","gоt Character series: $characterSeries")
            Result.success(characterSeries)
        }catch (e:Exception){
            Log.e("getCharacterSeries",e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getAllSeries(loadedCount: Int): Result<List<SeriesModel>> {
        return try {
            val allSeries = mutableListOf<SeriesModel>()
            api.getAllSeries(offset = loadedCount.toString()).data?.results?.forEach {
                    result -> allSeries.add(result.toModel())
            }
            Result.success(allSeries)
        }catch (e:Exception){
            Log.e("getAllSeries",e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getSeriesByTitle(title: String): Result<List<SeriesModel>> {
        return try {
            val series = mutableListOf<SeriesModel>()
            api.getSeriesByTitle(title).data?.results?.forEach {
                    results ->   series.add(results.toModel())
            }
            Result.success(series)
        }catch (e:Exception){
            Log.e("getSeriesByTitle",e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getSeriesById(id: Int): Result<SeriesModel> {
        return try {
            Log.i("getSeriesById","Start get series")
            val result = api.getSeriesById(id.toString()).data!!.results[0]
            Log.i("getSeriesById","Series got")
            val convertedRes = result.toModel()
            Log.i("getSeriesById","Converted $convertedRes")
            Result.success(convertedRes)
        }catch (e:Exception){
            Log.e("getSeriesById",e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): Result<List<SeriesModel>> {
        return try {
            val series = mutableListOf<SeriesModel>()
            Log.i("getConnectedSeries","Start get connected series ")
            connectedSeriesId.filterNotNull().forEach {
                series.add(api.getSeriesById(it.toString()).data!!.results[0].toModel())
            }
            Log.i("getConnectedSeries","Start get connected got ")
            Result.success(series)
        }catch (e:Exception){
            Log.e("getConnectedSeries",e.toString())
            Result.failure(e)
        }
    }


    override suspend fun loadMayLikeSeriesIds(loadedIdsSeriesFromBD: List<Int>): Result<List<Int>> {
        return try {
            val mayLikeSeries = mutableListOf<Int>()
            val series = coroutineScope {
                loadedIdsSeriesFromBD.map { id ->
                    async {
                        getSeriesById(id).fold(
                            onSuccess = {it},
                            onFailure = {throw Exception()}
                        )
                    }
                }
            }.awaitAll()
            if (loadedIdsSeriesFromBD.isNotEmpty() && series.isEmpty()) throw Exception("Error while fetching")
            series.forEach {
                val connected = it.connectedSeries.filterNotNull()
                mayLikeSeries.addAll(connected)
            }
            Result.success(mayLikeSeries)
        }catch (e:Exception){
            Log.e("loadMayLikeSeriesIds",e.toString())
            Result.failure(e)
        }
    }


    override suspend fun fetchSeries(ids: List<Int>): Result<List<SeriesModel>> {
        return try {
            val seriesDef = ids.map { id ->
                withContext(Dispatchers.IO){
                    async {
                        getSeriesById(id).fold(
                            onFailure = { null },
                            onSuccess = {it}
                        )
                    }
                }
            }
            val series = seriesDef.awaitAll().filterNotNull()
            if (ids.isNotEmpty() && series.isEmpty()) throw Exception("Error while fetching")
            Result.success(series)
        }catch (e:Exception){
            Log.e("fetchSeries",e.toString())
            Result.failure(e)
        }
    }
}