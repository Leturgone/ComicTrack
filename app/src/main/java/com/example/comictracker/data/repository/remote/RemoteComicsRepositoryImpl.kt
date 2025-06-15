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


    override suspend fun getSeriesLastReleasesById(id: Int): Result<List<ComicModel>> {
        return try {
            val seriesLastReleases = mutableListOf<ComicModel>()
            api.getSeriesLastReleasesById(series = id.toString()).data?.results?.forEach {
                    results ->  seriesLastReleases.add(results.toModel())
            }
            Result.success(seriesLastReleases)
        }catch (e:Exception){
            Log.e("getSeriesLastReleasesById", e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getComicsFromSeries(seriesId: Int,loadedCount: Int): Result<List<ComicModel>> {
        return try {
            val comics = mutableListOf<ComicModel>()
            api.getComicsFromSeries(seriesId.toString(), offset = loadedCount.toString()).data!!.results.forEach {
                    result ->  comics.add(result.toModel())
            }
            Result.success(comics)
        }catch (e:Exception){
            Log.e("getComicsFromSeries", e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getComicById(comicId: Int): Result<ComicModel> {
        return try {
            Log.i("getComicById","Start get comic ")
            val result = api.getComicById(comicId.toString()).data!!.results[0]
            val convertedRes = result.toModel()
            Log.i("getComicById","Converted $convertedRes ")
            Result.success(convertedRes)
        }catch (e:Exception){
            Log.e("getComicById", e.toString())
            Result.failure(e)
        }
    }


    override suspend fun fetchComics(ids: List<Int>): Result<List<ComicModel>> {
        return try{
            val comicsDef = ids.map { id ->
                withContext(Dispatchers.IO){
                    async {
                        getComicById(id).fold(
                            onSuccess = {it},
                            onFailure = {null}
                        )
                    }
                }
            }
            val filteredComic = comicsDef.awaitAll().filterNotNull()
            Result.success(filteredComic)
        }catch (e:Exception){
            Log.e("fetchComics", e.toString())
            Result.failure(e)
        }
    }


    override suspend fun fetchUpdatesForSeries(ids: List<Int>): Result<List<ComicModel>> {
        return try {
            val newComicsDef = ids.map { id ->
                withContext(Dispatchers.IO){
                    async {
                        getSeriesLastReleasesById(id).fold(
                            onSuccess = {it},
                            onFailure = { emptyList() }
                        )
                    }
                }
            }
            val flattenComics = newComicsDef.awaitAll().flatten()
            Result.success(flattenComics)
        }catch (e:Exception){
            Log.e("fetchUpdatesForSeries", e.toString())
            Result.failure(e)
        }
    }


    override suspend fun getPreviousComicId(seriesId: Int, number: Int): Result<Int?> {
        return try {
            Log.i("getPreviousComicId",seriesId.toString())
            val nextComic =api.getSpecificComicsFromSeries(
                seriesId = seriesId.toString(),
                issueNumber = (number-1).toString(),
                offset = "0"
            )
            try {
                val prevComicId = nextComic.data?.let { it.results[0].id.toIntOrNull() }
                Result.success(prevComicId)
            }catch (e:Exception){
                Result.success(null)
            }
        }catch (e:Exception){
            Log.e("getPreviousComicId", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getNextComicId(seriesId: Int, number: Int): Result<Int?> {
        return try {
            Log.i("NEXT",seriesId.toString())
            val nextComic =api.getSpecificComicsFromSeries(
                seriesId = seriesId.toString(),
                issueNumber = (number+1).toString(),
                offset = "0"
            )
            try {
                val nextComicId = nextComic.data?.let { it.results[0].id.toIntOrNull() }
                Result.success(nextComicId)
            }catch (e:Exception){
                Result.success(null)
            }
        }catch (e:Exception){
            Log.e("getNextComicId", e.toString())
            Result.failure(e)
        }
    }
}