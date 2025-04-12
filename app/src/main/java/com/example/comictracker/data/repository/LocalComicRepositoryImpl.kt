package com.example.comictracker.data.repository

import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.domain.repository.LocalComicRepository

class LocalComicRepositoryImpl(
    private val comicsDao: ComicsDao,
    private val seriesDao: SeriesDao,
    private val seriesListDao: SeriesListDao
): LocalComicRepository {

}