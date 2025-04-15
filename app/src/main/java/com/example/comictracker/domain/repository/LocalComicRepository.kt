package com.example.comictracker.domain.repository

import com.example.comictracker.domain.model.StatisticsforAll

interface LocalComicRepository {
    suspend fun loadCurrentReadIds():List<Int>
    suspend fun loadNextReadComicIds():List<Int>
    suspend fun loadLastComicIds():List<Int>
    suspend fun loadAllReadComicIds():List<Int>

    suspend fun loadAllReadSeriesIds():List<Int>

    suspend fun loadWillBeReadIds():List<Int>

    suspend fun loadStatistics():StatisticsforAll

    suspend fun loadFavoritesIds():List<Int>

    suspend fun loadComicMark(apiId: Int):String

    suspend fun loadSeriesMark(apiId:Int):String

    suspend fun markSeriesRead(apiId: Int):Boolean

    suspend fun markComicRead(apiId: Int,seriesApiId:Int):Boolean

    suspend fun addSeriesToFavorite(apiId: Int):Boolean

    suspend fun addSeriesToCurrentlyRead(apiId: Int):Boolean

    suspend fun addSeriesToWillBeRead(apiId: Int):Boolean

    suspend fun markComicUnread(apiId: Int):Boolean
    
    suspend fun markSeriesUnread(apiId: Int):Boolean

}