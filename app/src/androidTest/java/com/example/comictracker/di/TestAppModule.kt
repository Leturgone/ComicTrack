package com.example.comictracker.di

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
import org.mockito.Mockito
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {
    @Provides
    @Singleton
    fun provideRemoteComicsRepository(): RemoteComicsRepository {
        return Mockito.mock(RemoteComicsRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteSeriesRepository(): RemoteSeriesRepository {
        return Mockito.mock(RemoteSeriesRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteCreatorsRepository(): RemoteCreatorsRepository {
        return Mockito.mock(RemoteCreatorsRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteCharactersRepository(): RemoteCharacterRepository {
        return Mockito.mock(RemoteCharacterRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalReadRepository(): LocalReadRepository {
        return Mockito.mock(LocalReadRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalWriteRepository(): LocalWriteRepository {
        return Mockito.mock(LocalWriteRepository::class.java)
    }
}