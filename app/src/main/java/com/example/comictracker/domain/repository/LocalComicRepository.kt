package com.example.comictracker.domain.repository

import com.example.comictracker.domain.model.StatisticsforAll

interface LocalComicRepository {
    suspend fun loadCurrentReadIds():List<Int>

    suspend fun loadNextReadComicIds():List<Int>

    suspend fun loadLastComicIds():List<Int>

    suspend fun loadHistory():List<Int>

    suspend fun loadAllReadComicIds():List<Int>

    suspend fun loadAllReadSeriesIds():List<Int>

    suspend fun loadWillBeReadIds():List<Int>

    suspend fun loadStatistics():StatisticsforAll

    suspend fun loadFavoritesIds():List<Int>

    suspend fun loadComicMark(apiId: Int):String

    suspend fun loadSeriesMark(apiId:Int):String

    suspend fun loadSeriesFavoriteMark(apiId: Int):Boolean

    suspend fun markSeriesRead(apiId: Int):Boolean

    suspend fun markComicRead(apiId: Int,seriesApiId:Int,nextComicApiId:Int?):Boolean

    suspend fun addSeriesToFavorite(apiId: Int):Boolean

    suspend fun removeSeriesFromFavorite(apiId: Int):Boolean

    suspend fun addSeriesToCurrentlyRead(apiId: Int,firstIssueId:Int?):Boolean

    suspend fun addSeriesToWillBeRead(apiId: Int):Boolean

    suspend fun markComicUnread(apiId: Int,seriesApiId:Int,prevComicApiId:Int?):Boolean
    
    suspend fun markSeriesUnread(apiId: Int):Boolean

    suspend fun loadNextRead(apiId: Int):Int?

}