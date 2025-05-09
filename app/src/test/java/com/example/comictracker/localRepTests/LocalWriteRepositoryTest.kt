package com.example.comictracker.localRepTests

import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.data.repository.local.LocalWriteRepositoryImpl
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalWriteRepositoryTest {
    private lateinit var comicsDao: ComicsDao
    private lateinit var seriesDao: SeriesDao
    private lateinit var seriesListDao: SeriesListDao
    private lateinit var localWriteRepository: LocalWriteRepository

    @Before
    fun setUp(){
        comicsDao = Mockito.mock(ComicsDao::class.java)
        seriesDao = Mockito.mock(SeriesDao::class.java)
        seriesListDao = Mockito.mock(SeriesListDao::class.java)
        localWriteRepository = LocalWriteRepositoryImpl(comicsDao,seriesDao,seriesListDao)
    }

    @Test
    fun markSeriesReadSuccessTest() = runTest{

        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity(11))

        Mockito.`when`(
            seriesListDao.isSeriesInList(11)
        ).thenReturn(false)

        val result = localWriteRepository.markSeriesRead(11)
        assertTrue(result)
    }

    @Test
    fun markSeriesReadErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)

        val result = localWriteRepository.markSeriesRead(11)
        assertFalse(result)
    }

    @Test
    fun markComicReadSuccessTest(){}

    @Test
    fun markComicReadErrorTest(){}

    @Test
    fun addSeriesToFavoriteSuccessTest(){}

    @Test
    fun addSeriesToFavoriteErrorTest(){}

    @Test
    fun removeSeriesFromFavoriteSuccessTest(){}

    @Test
    fun removeSeriesFromFavoriteErrorTest(){}

    @Test
    fun addSeriesToCurrentlyReadSuccessTest(){}

    @Test
    fun addSeriesToCurrentlyReadErrorTest(){}

    @Test
    fun addSeriesToWillBeReadSuccessTest(){}

    @Test
    fun addSeriesToWillBeReadErrorTest(){}

    @Test
    fun markComicUnreadSuccessTest(){}

    @Test
    fun markComicUnreadErrorTest(){}

    @Test
    fun markSeriesUnreadSuccessTest(){}

    @Test
    fun markSeriesUnreadErrorTest(){}



}