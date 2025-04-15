package com.example.comictracker.data.repository

import android.util.Log
import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.database.enteties.ComicsEntity
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.domain.model.StatisticsforAll
import com.example.comictracker.domain.repository.LocalComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class LocalComicRepositoryImpl(
    private val comicsDao: ComicsDao,
    private val seriesDao: SeriesDao,
    private val seriesListDao: SeriesListDao
): LocalComicRepository {
    override suspend fun loadCurrentReadIds(): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getCurrentlyReadingSeriesApiIds()
        }
    }

    override suspend fun loadNextReadComicIds(): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getNextComicsForSeries().filterNotNull()
        }
    }

    override suspend fun loadLastComicIds(): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getLastReadComicsForSeries().filterNotNull()
        }
    }

    override suspend fun loadHistory(): List<Int> {
        return withContext(Dispatchers.IO){
            comicsDao.getHistory().filterNotNull()
        }
    }

    override suspend fun loadAllReadComicIds(): List<Int> {
        return withContext(Dispatchers.IO){
            comicsDao.getReadComicApiIds()
        }
    }

    override suspend fun loadAllReadSeriesIds(): List<Int> {
        return  withContext(Dispatchers.IO){
            seriesListDao.getReadSeriesApiIds()
        }
    }

    override suspend fun loadWillBeReadIds(): List<Int> {
        return  withContext(Dispatchers.IO){
            seriesListDao.getWillBeReadSeriesApiIds()
        }
    }

    override suspend fun loadStatistics(): StatisticsforAll {
        return withContext(Dispatchers.IO) {
            val comicCountDef = async { comicsDao.getComicsCount() }
            val seriesListCountDef = async { seriesListDao.getReadSeriesCount() }
            val readlistCountDef = async { seriesListDao.getWillBeReadSeriesCount() }

            val comicCount = comicCountDef.await()
            val seriesListCount = seriesListCountDef.await()
            val readlistCount = readlistCountDef.await()

            StatisticsforAll(
                comicCount =  comicCount,
                comicCountThisYear = comicCount,
                seriesCount = seriesListCount,
                seriesCountThisYear = seriesListCount,
                readlistCount = readlistCount
            )
        }
    }

    override suspend fun loadFavoritesIds(): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getFavoriteSeriesApiIds()
        }
    }

    override suspend fun loadComicMark(apiId: Int): String {
        return withContext(Dispatchers.IO){
            comicsDao.getComicMark(apiId)?:"unread"
        }
    }

    override suspend fun loadSeriesMark(apiId: Int): String {
        return withContext(Dispatchers.IO){
            seriesListDao.getSeriesMark(apiId)?:"unread"
        }
    }

    override suspend fun loadSeriesFavoriteMark(apiId: Int): Boolean {
        return withContext(Dispatchers.IO){
            val mark = seriesListDao.getSeriesFavoriteMark(apiId)
            Log.i("BD",mark.toString())
            mark
        }
    }

    override suspend fun markSeriesRead(apiId: Int):Boolean {
        return withContext(Dispatchers.IO){
            try {
                if(seriesDao.getSeriesByApiId(apiId)==null){
                    seriesDao.addSeries(
                        SeriesEntity(seriesApiId = apiId))
                }
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                Log.i("Room","Get entity$entity")
                if (seriesListDao.isSeriesInList(apiId)){
                    seriesListDao.addToReadUpdate(entity.id)
                }else{
                    seriesListDao.addToRead(entity.id)
                }
                Log.d("Room", "markSeriesRead: Series with id ${entity.id} added to 'read' list.")
                true
            }catch (e:Exception){
                Log.e("Room",e.toString())
                false
            }
        }
    }

    override suspend fun markComicRead(apiId: Int,seriesApiId:Int,nextComicApiId:Int?):Boolean {
        return withContext(Dispatchers.IO){
            try {
                var entity = seriesDao.getSeriesByApiId(seriesApiId)
                if(entity==null) {
                    addSeriesToCurrentlyRead(seriesApiId,nextComicApiId)
                    entity = seriesDao.getSeriesByApiId(seriesApiId)
                }
                comicsDao.addComic(
                    ComicsEntity(comicApiId = apiId,
                        mark = "read",
                        Series_idSeries = entity!!.id
                    )
                )
                val comicEntity = comicsDao.getComicByApiId(apiId)
                seriesDao.setLastRead(seriesApiId,apiId)
                seriesDao.setNextRead(seriesApiId,nextComicApiId)
                Log.d("Room", "markComicRead: Comic with id ${comicEntity!!.id} $nextComicApiId mark 'read'.")
                true
            }catch (e:Exception){
                Log.e("Room",e.toString())
                false
            }
        }
    }

    override suspend fun addSeriesToFavorite(apiId: Int):Boolean {
        return withContext(Dispatchers.IO){
            try {
                if(seriesDao.getSeriesByApiId(apiId)==null){
                    seriesDao.addSeries(
                        SeriesEntity(seriesApiId = apiId))
                }
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                Log.i("Room","Get entity$entity")
                if (!seriesListDao.isSeriesInList(apiId)){
                    seriesListDao.addToRead(entity.id)
                }
                if (!seriesListDao.getSeriesFavoriteMark(apiId)){
                    seriesListDao.addToFavorites(entity.id)
                    Log.d("Room", "addSeriesToFavorite: Series with id ${entity.id} added to 'favorite' list.")
                    true
                }else{
                    false
                }

            }catch (e:Exception){
                Log.e("Room",e.toString())
                false
            }
        }
    }

    override suspend fun removeSeriesFromFavorite(apiId: Int): Boolean {
        return withContext(Dispatchers.IO){
            val entity = seriesDao.getSeriesByApiId(apiId)!!
            if(seriesListDao.getSeriesFavoriteMark(apiId)){
                seriesListDao.removeFromFavorites(entity.id)
                true
            }else{
                false
            }
        }
    }

    override suspend fun addSeriesToCurrentlyRead(apiId: Int,firstIssueId:Int?):Boolean {
        return withContext(Dispatchers.IO){
            try {
                if(seriesDao.getSeriesByApiId(apiId)==null){
                    seriesDao.addSeries(
                        SeriesEntity(seriesApiId = apiId))
                }
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                Log.i("Room","Get entity$entity")
                if (!seriesListDao.isSeriesInList(apiId)){
                    seriesListDao.addToRead(entity.id)
                }
                seriesListDao.addToCurrentlyReading(entity.id)
                seriesDao.setNextRead(apiId,firstIssueId)
                Log.d("Room", "markSeriesRead: Series with id ${entity.id} added to 'read' list.")
                true
            }catch (e:Exception){
                Log.e("Room",e.toString())
                false
            }
        }
    }

    override suspend fun addSeriesToWillBeRead(apiId: Int):Boolean {
        return withContext(Dispatchers.IO){
            try {
                if(seriesDao.getSeriesByApiId(apiId)==null){
                    seriesDao.addSeries(
                        SeriesEntity(seriesApiId = apiId))
                }
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                Log.i("Room","Get entity$entity")
                if (!seriesListDao.isSeriesInList(apiId)){
                    seriesListDao.addToRead(entity.id)
                }
                seriesListDao.addToWillBeRead(entity.id)
                Log.d("Room", "markSeriesRead: Series with id ${entity.id} added to 'read' list.")
                true
            }catch (e:Exception){
                Log.e("Room",e.toString())
                false
            }
        }
    }

    override suspend fun markComicUnread(apiId: Int,seriesApiId: Int, prevComicApiId:Int?):Boolean {
        return withContext(Dispatchers.IO){
            if (comicsDao.getComicByApiId(apiId)!==null){
                comicsDao.removeComic(apiId)
                val entity  = seriesDao.getSeriesByApiId(seriesApiId)
                if(entity!=null){
                    seriesDao.setNextRead(seriesApiId, entity.lastReadId)
                    seriesDao.setLastRead(seriesApiId,prevComicApiId)
                    true
                }else{
                    false
                }

            }else{
                false
            }
        }
    }

    override suspend fun markSeriesUnread(apiId: Int):Boolean {
        return withContext(Dispatchers.IO){
            if (seriesDao.getSeriesByApiId(apiId)!==null){
                seriesDao.removeSeries(apiId)
                true
            }else{
                false
            }
        }
    }

    override suspend fun loadNextRead(apiId: Int): Int? {
        return withContext(Dispatchers.IO){
            seriesDao.getNextRead(apiId)
        }
    }

}