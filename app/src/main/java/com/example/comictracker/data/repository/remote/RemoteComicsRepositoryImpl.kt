package com.example.comictracker.data.repository.remote

import android.util.Log
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.comicsDTO.Results
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class RemoteComicsRepositoryImpl @Inject constructor(private val api: MarvelComicApi):RemoteComicsRepository {

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    //Маппер
    private fun Results.toModel():ComicModel{
        return ComicModel(
            comicId = this.id.toInt(),
            title =  this.title,
            number =  this.issueNumber,
            image = "${this.thumbnail?.path}.${this.thumbnail?.extension}",
            seriesId = this.series!!.resourceURI!!.substringAfter("series/").toInt(),
            seriesTitle = this.series!!.name!!,
            date = formatDate(this.dates[0].date.toString()),
            creators = this.creators!!.items.map {
                Pair(
                    it.resourceURI!!.substringAfter("creators/").toInt(),
                    it.role!!)
            },
            characters = this.characters!!.items.mapNotNull {
                it.resourceURI?.substringAfter("characters/")?.toIntOrNull()
            },
            readMark = "unread"
        )
    }
    override suspend fun getSeriesLastReleasesById(id: Int): List<ComicModel> {
        val seriesLastReleases = mutableListOf<ComicModel>()
        api.getSeriesLastReleasesById(series = id.toString()).data?.results?.forEach {
                results ->  seriesLastReleases.add(results.toModel())
        }
        return seriesLastReleases
    }

    override suspend fun getComicsFromSeries(seriesId: Int,loadedCount: Int): List<ComicModel> {
        val comics = mutableListOf<ComicModel>()
        api.getComicsFromSeries(seriesId.toString(), offset = loadedCount.toString()).data!!.results.forEach {
                result ->  comics.add(result.toModel())
        }
        return comics
    }

    override suspend fun getComicById(comicId: Int): ComicModel {
        Log.i("Repository","Start get comic ")
        val result = api.getComicById(comicId.toString()).data!!.results[0]
        Log.i("Repository","got comic ")
        val convertedRes = result.toModel()
        Log.i("Repository","Converted $convertedRes ")
        return convertedRes
    }

    override suspend fun fetchComics(ids: List<Int>): List<ComicModel> {
        val comicsDef = ids.map { id ->
            withContext(Dispatchers.IO){
                async {
                    try {
                        getComicById(id)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
        }
        return comicsDef.awaitAll().filterNotNull()
    }

    override suspend fun fetchUpdatesForSeries(ids: List<Int>): List<ComicModel> {
        val newComicsDef = ids.map { id ->
            withContext(Dispatchers.IO){
                async {
                    try {
                        getSeriesLastReleasesById(id)
                    } catch (e: Exception) {
                        emptyList()
                    }
                }
            }
        }
        return newComicsDef.awaitAll().flatten()
    }

    override suspend fun getPreviousComicId(seriesId: Int, number: Int): Int? {
        Log.i("PREV",seriesId.toString())
        val nextComic =api.getSpecificComicsFromSeries(
            seriesId = seriesId.toString(),
            issueNumber = (number-1).toString(),
            offset = "0"
        )
        return try {
            nextComic.data?.let { it.results[0].id.toIntOrNull() }
        }catch (e:Exception){
            null
        }
    }

    override suspend fun getNextComicId(seriesId: Int, number: Int): Int? {
        Log.i("NEXT",seriesId.toString())
        val nextComic =api.getSpecificComicsFromSeries(
            seriesId = seriesId.toString(),
            issueNumber = (number+1).toString(),
            offset = "0"
        )
        return try {
            nextComic.data?.let { it.results[0].id.toIntOrNull() }
        }catch (e:Exception){
            null
        }
    }
}