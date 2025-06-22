package com.example.comictracker.di

import android.content.Context
import androidx.room.Room
import com.example.comictracker.data.database.ComicTrackerDatabase
import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideComicTrackerDatabase(@ApplicationContext context: Context): ComicTrackerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ComicTrackerDatabase::class.java,
            "comic_track_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideComicsDao(db: ComicTrackerDatabase): ComicsDao {
        return db.comicsDao()
    }

    @Provides
    @Singleton
    fun provideSeriesDao(db: ComicTrackerDatabase): SeriesDao {
        return db.seriesDao()
    }

    @Provides
    @Singleton
    fun provideSeriesListDao(db: ComicTrackerDatabase): SeriesListDao {
        return db.seriesListDao()
    }

}