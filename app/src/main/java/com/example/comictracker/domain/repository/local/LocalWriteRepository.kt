package com.example.comictracker.domain.repository.local

interface LocalWriteRepository {
    suspend fun markSeriesRead(apiId: Int):Boolean

    suspend fun markComicRead(apiId: Int,seriesApiId:Int,nextComicApiId:Int?):Boolean

    suspend fun addSeriesToFavorite(apiId: Int):Boolean

    suspend fun removeSeriesFromFavorite(apiId: Int):Boolean

    suspend fun addSeriesToCurrentlyRead(apiId: Int,firstIssueId:Int?):Boolean

    suspend fun addSeriesToWillBeRead(apiId: Int):Boolean

    suspend fun markComicUnread(apiId: Int,seriesApiId:Int,prevComicApiId:Int?):Boolean

    suspend fun markSeriesUnread(apiId: Int):Boolean
}