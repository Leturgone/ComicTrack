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
    override suspend fun markSeriesRead(apiId: Int): Result<Unit> {
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
                        return@withContext Result.failure(Exception("Series already read"))
                    }
                }else{
                    seriesListDao.addToRead(entity.id)
                }
                Result.success(Unit)
            }catch (e:Exception){
                Log.e("markSeriesRead",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun markComicRead(apiId: Int,seriesApiId:Int,nextComicApiId:Int?): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                var entity = seriesDao.getSeriesByApiId(seriesApiId)
                if(entity==null) {
                    addSeriesToCurrentlyRead(seriesApiId,nextComicApiId)
                    entity = seriesDao.getSeriesByApiId(seriesApiId)
                }
                if (comicsDao.getComicByApiId(apiId)!=null) throw AlreadyMarkedException()
                comicsDao.addComic(
                    ComicsEntity(comicApiId = apiId,
                        mark = "read",
                        Series_idSeries = entity!!.id
                    )
                )
                comicsDao.getComicByApiId(apiId)?:throw ComicNotFoundException()
                seriesDao.setLastRead(seriesApiId,apiId)
                seriesDao.setNextRead(seriesApiId,nextComicApiId)
                Result.success(Unit)
            }
            catch (e:AlreadyMarkedException){
                Log.e("addSeriesToCurrentlyRead","Comic already read")
                Result.failure(e)
            }
            catch (e:ComicNotFoundException){
                Log.e("addSeriesToCurrentlyRead","Error while adding comic")
                Result.failure(e)
            }
            catch (e:Exception){
                Log.e("markComicRead",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun addSeriesToFavorite(apiId: Int): Result<Unit> {
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
                    Result.success(Unit)
                }else{
                    Result.failure(Exception())
                }

            }catch (e:Exception){
                Log.e("addSeriesToFavorite",e.toString())
                Result.failure(Exception())
            }
        }
    }


    override suspend fun removeSeriesFromFavorite(apiId: Int): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                if(seriesListDao.getSeriesFavoriteMark(apiId)){
                    seriesListDao.removeFromFavorites(entity.id)
                    Result.success(Unit)
                }else{
                    Result.failure(Exception())
                }
            }catch (e:Exception){
                Log.e("removeSeriesFromFavorite",e.toString())
                Result.failure(e)
            }

        }
    }


    override suspend fun addSeriesToCurrentlyRead(apiId: Int,firstIssueId:Int?):Result<Unit> {
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
                    Result.success(Unit)
                }else{
                    throw AlreadyMarkedException()
                }
            }
            catch (e:AlreadyMarkedException){
                Log.e("addSeriesToCurrentlyRead","Series already currently read")
                Result.failure(e)
            }
            catch (e:Exception){
                Log.e("addSeriesToCurrentlyRead",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun addSeriesToWillBeRead(apiId: Int):Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                if(seriesDao.getSeriesByApiId(apiId)==null){
                    seriesDao.addSeries(
                        SeriesEntity(seriesApiId = apiId))
                }
                val entity = seriesDao.getSeriesByApiId(apiId)!!
                Log.i("addSeriesToWillBeRead","Get entity $entity")
                if (!seriesListDao.isSeriesInList(apiId)){
                        seriesListDao.addToRead(entity.id)
                }
                if (!seriesListDao.checkAlreadyWillBeRead(entity.id)){
                    seriesListDao.addToWillBeRead(entity.id)
                    Log.d("addSeriesToWillBeRead", "Series with id ${entity.id} added to 'will' list.")
                    Result.success(Unit)
                }else{
                    throw AlreadyMarkedException()
                }
            }
            catch (e:AlreadyMarkedException){
                Log.e("addSeriesToWillBeRead","Series already will be read")
                Result.failure(e)
            }
            catch (e:Exception){
                Log.e("addSeriesToWillBeRead",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun markComicUnread(apiId: Int,seriesApiId: Int, prevComicApiId:Int?): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                comicsDao.getComicByApiId(apiId)?:throw SeriesNotFoundException()
                comicsDao.removeComic(apiId)
                val entity  = seriesDao.getSeriesByApiId(seriesApiId)?:throw EntityNotFoundException()
                seriesDao.setNextRead(seriesApiId, entity.lastReadId)
                seriesDao.setLastRead(seriesApiId,prevComicApiId)
                Result.success(Unit)
            }
            catch (e:SeriesNotFoundException){
                Log.e("markComicUnread","Series not found")
                Result.failure(e)
            }
            catch (e:EntityNotFoundException){
                Log.e("markComicUnread","Entity not found")
                Result.failure(e)
            }
            catch (e:Exception){
                Log.e("markComicUnread",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun markSeriesUnread(apiId: Int): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                seriesDao.getSeriesByApiId(apiId)?:throw SeriesNotFoundException()
                seriesDao.removeSeries(apiId)
                Result.success(Unit)

            }catch (e:SeriesNotFoundException){
                Log.e("markSeriesUnread","Series not found")
                Result.failure(e)
            }
            catch (e:Exception){
                Log.e("markSeriesUnread",e.toString())
                Result.failure(e)
            }
        }
    }

}