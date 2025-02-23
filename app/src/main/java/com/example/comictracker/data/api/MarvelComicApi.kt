package com.example.comictracker.data.api

import retrofit2.http.GET
interface MarvelComicApi {

    suspend fun getSeriesLastReleasesById()
    suspend fun getSeriesByCharacterId()
    suspend fun getAllSeries()
    suspend fun getAllCharacters()
    suspend fun getSeriesByTitle()

    suspend fun getSeriesById()
    suspend fun getSeriesCreators()
    suspend fun getSeriesCharacters()
    suspend fun getConnectedSeries()
    suspend fun getComicsFromSeries()

    suspend fun getComicById()
    suspend fun getComicCreators()
    suspend fun getComicCharacters()


}