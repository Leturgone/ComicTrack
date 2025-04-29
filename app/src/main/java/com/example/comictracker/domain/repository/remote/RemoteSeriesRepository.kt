package com.example.comictracker.domain.repository.remote

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel

interface RemoteSeriesRepository {
    suspend fun getCharacterSeries(characterId: Int,offset:Int = 0): List<SeriesModel>

    suspend fun getAllSeries(loadedCount: Int = 0):List<SeriesModel>

    suspend fun getSeriesByTitle(title:String):List<SeriesModel>

    suspend fun getSeriesById(id:Int):SeriesModel

    suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): List<SeriesModel>

    suspend fun loadMayLikeSeriesIds(loadedIdsSeriesFromBD:List<Int>):List<Int>

    suspend fun fetchSeries(ids: List<Int>): List<SeriesModel>

}