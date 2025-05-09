package com.example.comictracker.localRepTests

import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.repository.local.LocalWriteRepositoryImpl
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

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
    fun markSeriesReadSuccessTest(){}

    @Test
    fun markSeriesReadErrorTest(){}

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