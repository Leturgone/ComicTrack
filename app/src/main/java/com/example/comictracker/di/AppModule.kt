package com.example.comictracker.di

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.repository.RemoteComicRepositoryImpl
import com.example.comictracker.domain.repository.RemoteComicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMarvelComicApi():MarvelComicApi{
        return Retrofit
            .Builder().baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelComicApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRemoteRepository(api: MarvelComicApi):RemoteComicRepository{
        return RemoteComicRepositoryImpl(api)
    }

}