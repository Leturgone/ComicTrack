package com.example.comictracker.localRepTests

import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.database.enteties.ComicsEntity
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.data.repository.local.LocalWriteRepositoryImpl
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import kotlinx.coroutines.test.runTest
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
        assertTrue(result.isSuccess)
    }

    @Test
    fun markSeriesReadErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)

        val result = localWriteRepository.markSeriesRead(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun markComicReadSuccessTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        var callCount = 0
        Mockito.`when`(
            (comicsDao.getComicByApiId(12))
        ).thenAnswer{
            callCount++
            when(callCount){
                2 -> ComicsEntity()
                else -> {null}
            }
        }

        val result = localWriteRepository.markComicRead(12,11,null)
        assertTrue(result.isSuccess)
    }


    @Test
    fun markComicReadAlreadyExistTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            (comicsDao.getComicByApiId(12))
        ).thenReturn(ComicsEntity())

        val result = localWriteRepository.markComicRead(12,11,null)
        assertTrue(result.isFailure)
    }

    @Test
    fun markComicReadErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            (comicsDao.getComicByApiId(12))
        ).thenReturn(null)

        val result = localWriteRepository.markComicRead(12,11,null)
        assertTrue(result.isFailure)
    }

    @Test
    fun addSeriesToFavoriteSuccessTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            seriesListDao.getSeriesFavoriteMark(11)
        ).thenReturn(false)

        val result = localWriteRepository.addSeriesToFavorite(11)
        assertTrue(result.isSuccess)
    }
    @Test
    fun addSeriesToFavoriteAlreadyMarkedTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            seriesListDao.getSeriesFavoriteMark(11)
        ).thenReturn(true)

        val result = localWriteRepository.addSeriesToFavorite(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun addSeriesToFavoriteErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)

        val result = localWriteRepository.addSeriesToFavorite(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun removeSeriesFromFavoriteSuccessTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            seriesListDao.getSeriesFavoriteMark(11)
        ).thenReturn(true)

        val result = localWriteRepository.removeSeriesFromFavorite(11)
        assertTrue(result.isSuccess)
    }

    @Test
    fun removeSeriesFromFavoriteAlreadyNotFavoriteTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            seriesListDao.getSeriesFavoriteMark(11)
        ).thenReturn(false)

        val result = localWriteRepository.removeSeriesFromFavorite(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun removeSeriesFromFavoriteErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)

        val result = localWriteRepository.removeSeriesFromFavorite(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun addSeriesToCurrentlyReadSuccessTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())
        Mockito.`when`(
            seriesListDao.isSeriesInList(11)
        ).thenReturn(false)
        Mockito.`when`(
            seriesListDao.checkAlreadyCurrentlyRead(1)
        ).thenReturn(false)

        val result = localWriteRepository.addSeriesToCurrentlyRead(11,null)
        assertTrue(result.isSuccess)
    }

    @Test
    fun addSeriesToCurrentlyReadAlreadyTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity(id = 1))

        Mockito.`when`(
            seriesListDao.checkAlreadyCurrentlyRead(1)
        ).thenReturn(true)

        val result = localWriteRepository.addSeriesToCurrentlyRead(11,null)
        assertTrue(result.isFailure)
    }

    @Test
    fun addSeriesToCurrentlyReadErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)
        val result = localWriteRepository.addSeriesToCurrentlyRead(11,null)
        assertTrue(result.isFailure)
    }

    @Test
    fun addSeriesToWillBeReadSuccessTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity(id = 1))
        Mockito.`when`(
            seriesListDao.isSeriesInList(11)
        ).thenReturn(false)
        Mockito.`when`(
            seriesListDao.checkAlreadyWillBeRead(1)
        ).thenReturn(false)

        val result = localWriteRepository.addSeriesToWillBeRead(11)
        assertTrue(result.isSuccess)
    }
    @Test
    fun addSeriesToWillBeReadAlreadyTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity(id = 1))
        Mockito.`when`(
            seriesListDao.isSeriesInList(11)
        ).thenReturn(false)
        Mockito.`when`(
            seriesListDao.checkAlreadyWillBeRead(1)
        ).thenReturn(true)

        val result = localWriteRepository.addSeriesToWillBeRead(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun addSeriesToWillBeReadErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)
        val result = localWriteRepository.addSeriesToWillBeRead(11)
        assertTrue(result.isFailure)
    }

    @Test
    fun markComicUnreadSuccessTest() = runTest{
        Mockito.`when`(
            comicsDao.getComicByApiId(11)
        ).thenReturn(ComicsEntity())
        Mockito.`when`(
            seriesDao.getSeriesByApiId(12)
        ).thenReturn(SeriesEntity())

        val result = localWriteRepository.markComicUnread(11,12,null)
        assertTrue(result.isSuccess)
    }

    @Test
    fun markComicUnreadErrorComicNotFoundTest() = runTest{
        Mockito.`when`(
            comicsDao.getComicByApiId(11)
        ).thenReturn(null)

        val result = localWriteRepository.markComicUnread(11,12,null)
        assertTrue(result.isFailure)
    }
    @Test
    fun markComicUnreadErrorSeriesNotFoundTest() = runTest{
        Mockito.`when`(
            comicsDao.getComicByApiId(11)
        ).thenReturn(ComicsEntity())
        Mockito.`when`(
            seriesDao.getSeriesByApiId(12)
        ).thenReturn(null)

        val result = localWriteRepository.markComicUnread(11,12,null)
        assertTrue(result.isFailure)
    }

    @Test
    fun markSeriesUnreadSuccessTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(SeriesEntity())

        val result = localWriteRepository.markSeriesUnread(11)
        assertTrue(result.isSuccess)
    }

    @Test
    fun markSeriesUnreadErrorTest() = runTest{
        Mockito.`when`(
            seriesDao.getSeriesByApiId(11)
        ).thenReturn(null)

        val result = localWriteRepository.markSeriesUnread(11)
        assertTrue(result.isFailure)
    }
}