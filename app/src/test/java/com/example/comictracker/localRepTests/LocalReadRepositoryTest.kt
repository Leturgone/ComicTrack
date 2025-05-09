package com.example.comictracker.localRepTests

import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.repository.local.LocalReadRepositoryImpl
import com.example.comictracker.domain.repository.local.LocalReadRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class LocalReadRepositoryTest {
    private lateinit var comicsDao: ComicsDao
    private lateinit var seriesDao: SeriesDao
    private lateinit var seriesListDao: SeriesListDao
    private lateinit var localReadRepository:LocalReadRepository

    @Before
    fun setUp(){
        comicsDao = Mockito.mock(ComicsDao::class.java)
        seriesDao = Mockito.mock(SeriesDao::class.java)
        seriesListDao = Mockito.mock(SeriesListDao::class.java)
        localReadRepository = LocalReadRepositoryImpl(comicsDao,seriesDao,seriesListDao)
    }


    @Test
    fun loadCurrentReadIdsSuccessTest() = runTest{
        Mockito.`when`(
            seriesListDao.getCurrentlyReadingSeriesApiIds(0)
        ).thenReturn(listOf(1,2,3))

        val result = localReadRepository.loadCurrentReadIds(0)
        assertEquals(listOf(1,2,3),result)
    }

    @Test
    fun loadCurrentReadIdsErrorTest() = runTest{
        Mockito.`when`(
            seriesListDao.getCurrentlyReadingSeriesApiIds(0)
        ).thenReturn(emptyList())

        val result = localReadRepository.loadCurrentReadIds(0)
        assertEquals(emptyList<Int>(),result)
    }

    @Test
    fun loadNextReadComicIdsSuccessTest() = runTest{
        Mockito.`when`(
            seriesListDao.getNextComicsForSeries(0)
        ).thenReturn(listOf(1,2,3))

        val result = localReadRepository.loadNextReadComicIds(0)
        assertEquals(listOf(1,2,3),result)
    }

    @Test
    fun loadNextReadComicIdsErrorTest() = runTest {
        Mockito.`when`(
            seriesListDao.getNextComicsForSeries(0)
        ).thenReturn(listOf(1,null,2,null))

        val result = localReadRepository.loadNextReadComicIds(0)
        assertEquals(listOf(1,2),result)
    }

    @Test
    fun loadHistorySuccessTest() = runTest{
        Mockito.`when`(
            comicsDao.getHistory(0)
        ).thenReturn(listOf(1,2,3))

        val result = localReadRepository.loadHistory(0)
        assertEquals(listOf(1,2,3),result)
    }

    @Test
    fun loadHistoryErrorTest() = runTest{
        Mockito.`when`(
            comicsDao.getHistory(0)
        ).thenReturn(listOf(null,null))

        val result = localReadRepository.loadHistory(0)
        assertEquals(emptyList<Int>(),result)
    }

    @Test
    fun loadAllReadComicIdsSuccessTest(){}

    @Test
    fun loadAllReadComicIdsErrorTest(){}

    @Test
    fun loadAllReadSeriesIdsSuccessTest(){}

    @Test
    fun loadAllReadSeriesIdsErrorTest(){}

    @Test
    fun loadWillBeReadIdsSuccessTest(){}

    @Test
    fun loadWillBeReadIdsErrorTest(){}

    @Test
    fun loadStatisticsSuccessTest(){}

    @Test
    fun loadStatisticsErrorTest(){}

    @Test
    fun loadFavoritesIdsSuccessTest(){}

    @Test
    fun loadFavoritesIdsErrorTest(){}

    @Test
    fun loadComicMarkSuccessTest(){}

    @Test
    fun loadComicMarkErrorTest(){}

    @Test
    fun loadSeriesMarkSuccessTest(){}

    @Test
    fun loadSeriesMarkErrorTest(){}

    @Test
    fun loadSeriesFavoriteMarkSuccessTest(){}

    @Test
    fun loadSeriesFavoriteMarkErrorTest(){}

    @Test
    fun loadNextReadSuccessTest(){}

    @Test
    fun loadNextReadErrorTest(){}



}