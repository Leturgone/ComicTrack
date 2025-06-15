package com.example.comictracker.data.repository.local

import android.util.Log
import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.domain.model.StatisticsforAll
import com.example.comictracker.domain.repository.local.LocalReadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class LocalReadRepositoryImpl(
    private val comicsDao: ComicsDao,
    private val seriesDao: SeriesDao,
    private val seriesListDao: SeriesListDao
): LocalReadRepository {
    override suspend fun loadCurrentReadIds(offset:Int): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesListDao.getCurrentlyReadingSeriesApiIds(offset)
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadCurrentReadIds",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadNextReadComicIds(offset:Int): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesListDao.getNextComicsForSeries(offset).filterNotNull()
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadNextReadComicIds",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun loadHistory(offset:Int): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = comicsDao.getHistory(offset).filterNotNull()
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadHistory",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadAllReadComicIds(offset:Int): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = comicsDao.getReadComicApiIds(offset)
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadAllReadComicIds",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadAllReadSeriesIds(offset:Int): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesListDao.getReadSeriesApiIds(offset)
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadAllReadSeriesIds",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadWillBeReadIds(offset:Int): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesListDao.getWillBeReadSeriesApiIds(offset)
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadWillBeReadIds",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadStatistics(): Result<StatisticsforAll> {
        return withContext(Dispatchers.IO) {
            try {
                val comicCountDef = async { comicsDao.getComicsCount() }
                val seriesListCountDef = async { seriesListDao.getReadSeriesCount() }
                val readlistCountDef = async { seriesListDao.getWillBeReadSeriesCount() }

                val comicCount = comicCountDef.await()
                val seriesListCount = seriesListCountDef.await()
                val readlistCount = readlistCountDef.await()

                val result = StatisticsforAll(
                    comicCount =  comicCount,
                    comicCountThisYear = comicCount,
                    seriesCount = seriesListCount,
                    seriesCountThisYear = seriesListCount,
                    readlistCount = readlistCount
                )
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadStatistic",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadFavoritesIds(): Result<List<Int>> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesListDao.getFavoriteSeriesApiIds()
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadFavoritesIds",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadComicMark(apiId: Int): Result<String> {
        return withContext(Dispatchers.IO){
            try {
                val result = comicsDao.getComicMark(apiId)?:"unread"
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadComicMark",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadSeriesMark(apiId: Int): Result<String> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesListDao.getSeriesMark(apiId)?:"unread"
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadSeriesMark",e.toString())
                Result.failure(e)
            }
        }
    }

    override suspend fun loadSeriesFavoriteMark(apiId: Int): Result<Boolean> {
        return withContext(Dispatchers.IO){
            try {
                val mark = seriesListDao.getSeriesFavoriteMark(apiId)
                Result.success(mark)
            }catch (e:Exception){
                Log.e("loadSeriesFavoriteMark",e.toString())
                Result.failure(e)
            }
        }
    }


    override suspend fun loadNextRead(seriesApiId: Int): Result<Int?> {
        return withContext(Dispatchers.IO){
            try {
                val result = seriesDao.getNextRead(seriesApiId)
                Result.success(result)
            }catch (e:Exception){
                Log.e("loadNextRead",e.toString())
                Result.failure(e)
            }
        }
    }
}