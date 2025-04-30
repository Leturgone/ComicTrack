package com.example.comictracker.di

import android.content.Context
import androidx.room.Room
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.database.ComicTrackerDatabase
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMarvelComicApi():MarvelComicApi{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Увеличение времени ожидания соединения
            .readTimeout(60, TimeUnit.SECONDS)    // Увеличение времени ожидания чтения данных
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        return Retrofit
            .Builder().baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MarvelComicApi::class.java)
    }

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
    fun provideComicTrackerDatabase(@ApplicationContext context: Context): ComicTrackerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ComicTrackerDatabase::class.java,
            "comic_track_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideComicsDao(db:ComicTrackerDatabase): ComicsDao{
        return db.comicsDao()
    }

    @Provides
    @Singleton
    fun provideSeriesDao(db:ComicTrackerDatabase): SeriesDao{
        return db.seriesDao()
    }

    @Provides
    @Singleton
    fun provideSeriesListDao(db:ComicTrackerDatabase): SeriesListDao{
        return db.seriesListDao()
    }

    @Provides
    @Singleton
    fun provideLocalReadRepository(comicsDao: ComicsDao, seriesDao: SeriesDao,
                               seriesListDao: SeriesListDao): LocalReadRepository {
        return LocalReadRepositoryImpl(comicsDao, seriesDao, seriesListDao)
    }

    @Provides
    @Singleton
    fun provideLocalWriteRepository(comicsDao: ComicsDao, seriesDao: SeriesDao,
                                   seriesListDao: SeriesListDao): LocalWriteRepository {
        return LocalWriteRepositoryImpl(comicsDao, seriesDao, seriesListDao)
    }




}