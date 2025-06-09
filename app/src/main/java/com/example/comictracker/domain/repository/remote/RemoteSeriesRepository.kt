package com.example.comictracker.domain.repository.remote

import com.example.comictracker.domain.model.SeriesModel

interface RemoteSeriesRepository {
    suspend fun getCharacterSeries(characterId: Int,offset:Int = 0): Result<List<SeriesModel>>

    suspend fun getAllSeries(loadedCount: Int = 0): Result<List<SeriesModel>>

    suspend fun getSeriesByTitle(title:String): Result<List<SeriesModel>>

    suspend fun getSeriesById(id:Int): Result<SeriesModel>

    suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): Result<List<SeriesModel>>

    suspend fun loadMayLikeSeriesIds(loadedIdsSeriesFromBD:List<Int>): Result<List<Int>>

    suspend fun fetchSeries(ids: List<Int>): Result<List<SeriesModel>>

}