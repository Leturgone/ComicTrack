package com.example.comictracker.domain.repository.local

interface LocalWriteRepository {
    suspend fun markSeriesRead(apiId: Int): Result<Unit>

    suspend fun markComicRead(apiId: Int,seriesApiId:Int,nextComicApiId:Int?):Result<Unit>

    suspend fun addSeriesToFavorite(apiId: Int):Result<Unit>

    suspend fun removeSeriesFromFavorite(apiId: Int):Result<Unit>

    suspend fun addSeriesToCurrentlyRead(apiId: Int,firstIssueId:Int?):Result<Unit>

    suspend fun addSeriesToWillBeRead(apiId: Int):Result<Unit>

    suspend fun markComicUnread(apiId: Int,seriesApiId:Int,prevComicApiId:Int?):Result<Unit>

    suspend fun markSeriesUnread(apiId: Int):Result<Unit>
}