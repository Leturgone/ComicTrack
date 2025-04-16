package com.example.comictracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.database.enteties.ComicsEntity
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.data.database.enteties.SeriesListEntity


/**
 * Comic tracker database
 *
 * @constructor Create empty Comic tracker database
 */
@Database(entities = [ComicsEntity::class, SeriesEntity::class,SeriesListEntity::class], version = 1)
abstract class ComicTrackerDatabase :RoomDatabase(){
    /**
     * Comics dao
     *
     * @return
     */
    abstract fun comicsDao():ComicsDao

    /**
     * Series dao
     *
     * @return
     */
    abstract fun seriesDao():SeriesDao

    /**
     * Series list dao
     *
     * @return
     */
    abstract fun seriesListDao():SeriesListDao
}