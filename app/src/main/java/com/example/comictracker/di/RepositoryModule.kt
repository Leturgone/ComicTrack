package com.example.comictracker.di

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.database.dao.ComicsDao
import com.example.comictracker.data.database.dao.SeriesDao
import com.example.comictracker.data.database.dao.SeriesListDao
import com.example.comictracker.data.repository.local.LocalReadRepositoryImpl
import com.example.comictracker.data.repository.local.LocalWriteRepositoryImpl
import com.example.comictracker.data.repository.remote.RemoteCharacterRepositoryImpl
import com.example.comictracker.data.repository.remote.RemoteComicsRepositoryImpl
import com.example.comictracker.data.repository.remote.RemoteCreatorsRepositoryImpl
import com.example.comictracker.data.repository.remote.RemoteSeriesRepositoryImpl
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteComicsRepository(api: MarvelComicApi): RemoteComicsRepository {
        return RemoteComicsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRemoteSeriesRepository(api: MarvelComicApi): RemoteSeriesRepository {
        return RemoteSeriesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRemoteCreatorsRepository(api: MarvelComicApi): RemoteCreatorsRepository {
        return RemoteCreatorsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRemoteCharactersRepository(api: MarvelComicApi): RemoteCharacterRepository {
        return RemoteCharacterRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocalReadRepository(comicsDao: ComicsDao, seriesDao: SeriesDao,
                                   seriesListDao: SeriesListDao
    ): LocalReadRepository {
        return LocalReadRepositoryImpl(comicsDao, seriesDao, seriesListDao)
    }

    @Provides
    @Singleton
    fun provideLocalWriteRepository(comicsDao: ComicsDao, seriesDao: SeriesDao,
                                    seriesListDao: SeriesListDao
    ): LocalWriteRepository {
        return LocalWriteRepositoryImpl(comicsDao, seriesDao, seriesListDao)
    }
}