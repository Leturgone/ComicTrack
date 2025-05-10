package com.example.comictracker.data.repository.local

import android.util.Log
import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.database.enteties.ComicsEntity
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalWriteRepositoryImpl(
    private val comicsDao: ComicsDao,
    private val seriesDao: SeriesDao,
    private val seriesListDao: SeriesListDao
): LocalWriteRepository {
    override suspend fun markSeriesRead(apiId: Int):Boolean {
        return withContext(Dispatchers.IO){
            try {
                if(seriesDao.getSeriesByApiId(apiId)==null){
                    seriesDao.addSeries(
                        SeriesEntity(seriesApiId = apiId)
                    )
                }
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                Log.i("markSeriesRead","Get entity$entity")
                if (seriesListDao.isSeriesInList(apiId)){
                    if (!seriesListDao.checkAlreadyRead(entity.id)) {
                        seriesListDao.addToReadUpdate(entity.id)
                    }else{
                        Log.e("markSeriesRead","Series already read")
                        return@withContext false
                    }
                }else{
                    seriesListDao.addToRead(entity.id)
                }
                Log.d("markSeriesRead", "markSeriesRead: Series with id ${entity.id} added to 'read' list.")
                true
            }catch (e:Exception){
                Log.e("markSeriesRead",e.toString())
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
                if (comicsDao.getComicByApiId(apiId)!=null) return@withContext false
                comicsDao.addComic(
                    ComicsEntity(comicApiId = apiId,
                        mark = "read",
                        Series_idSeries = entity!!.id
                    )
                )
                val comicEntity = comicsDao.getComicByApiId(apiId)
                seriesDao.setLastRead(seriesApiId,apiId)
                seriesDao.setNextRead(seriesApiId,nextComicApiId)
                Log.d("markComicRead", "Comic with id ${comicEntity!!.id} $nextComicApiId mark 'read'.")
                true
            }catch (e:Exception){
                Log.e("markComicRead",e.toString())
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
                Log.i("addSeriesToFavorite","Get entity$entity")
                if (!seriesListDao.isSeriesInList(apiId)){
                    seriesListDao.addToRead(entity.id)
                }
                if (!seriesListDao.getSeriesFavoriteMark(apiId)){
                    seriesListDao.addToFavorites(entity.id)
                    Log.d("addSeriesToFavorite", "addSeriesToFavorite: Series with id ${entity.id} added to 'favorite' list.")
                    true
                }else{
                    false
                }

            }catch (e:Exception){
                Log.e("addSeriesToFavorite",e.toString())
                false
            }
        }
    }

    override suspend fun removeSeriesFromFavorite(apiId: Int): Boolean {
        return withContext(Dispatchers.IO){
            try {
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                if(seriesListDao.getSeriesFavoriteMark(apiId)){
                    seriesListDao.removeFromFavorites(entity.id)
                    true
                }else{
                    false
                }
            }catch (e:Exception){
                Log.e("removeSeriesFromFavorite",e.toString())
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
                Log.i("addSeriesToCurrentlyRead","Get entity$entity")

                if (!seriesListDao.isSeriesInList(apiId)){
                    seriesListDao.addToRead(entity.id)
                }

                if (!seriesListDao.checkAlreadyCurrentlyRead(entity.id)){
                    seriesListDao.addToCurrentlyReading(entity.id)
                    seriesDao.setNextRead(apiId,firstIssueId)
                    Log.d("Room", "addSeriesToCurrentlyRead: Series with id ${entity.id} added to 'currently' list.")
                    true
                }else{
                    Log.e("addSeriesToCurrentlyRead","Series already currently read")
                    false
                }
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
                if (!seriesListDao.checkAlreadyWillBeRead(entity.id)){
                    seriesListDao.addToWillBeRead(entity.id)
                    Log.d("addSeriesToWillBeRead", "Series with id ${entity.id} added to 'will' list.")
                    true
                }else{
                    Log.e("addSeriesToWillBeRead","Series already will be read")
                    false
                }
            }catch (e:Exception){
                Log.e("addSeriesToWillBeRead",e.toString())
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

}