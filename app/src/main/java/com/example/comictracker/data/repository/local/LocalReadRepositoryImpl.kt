package com.example.comictracker.data.repository.local

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
    override suspend fun loadCurrentReadIds(offset:Int): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getCurrentlyReadingSeriesApiIds(offset)
        }
    }

    override suspend fun loadNextReadComicIds(offset:Int): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getNextComicsForSeries(offset).filterNotNull()
        }
    }


    override suspend fun loadHistory(offset:Int): List<Int> {
        return withContext(Dispatchers.IO){
            comicsDao.getHistory(offset).filterNotNull()
        }
    }

    override suspend fun loadAllReadComicIds(offset:Int): List<Int> {
        return withContext(Dispatchers.IO){
            comicsDao.getReadComicApiIds(offset)
        }
    }

    override suspend fun loadAllReadSeriesIds(offset:Int): List<Int> {
        return withContext(Dispatchers.IO){
            seriesListDao.getReadSeriesApiIds(offset)
        }
    }

    override suspend fun loadWillBeReadIds(offset:Int): List<Int> {
        return  withContext(Dispatchers.IO){
            seriesListDao.getWillBeReadSeriesApiIds(offset)
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
            mark
        }
    }

    override suspend fun loadNextRead(seriesApiId: Int): Int? {
        return withContext(Dispatchers.IO){
            seriesDao.getNextRead(seriesApiId)
        }
    }
}