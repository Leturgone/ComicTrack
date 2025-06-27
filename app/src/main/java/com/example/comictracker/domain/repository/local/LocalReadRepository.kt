package com.example.comictracker.domain.repository.local

import com.example.comictracker.domain.model.StatisticsforAll

interface LocalReadRepository {

    suspend fun loadCurrentReadIds(offset:Int): Result<List<Int>>

    suspend fun loadNextReadComicIds(offset:Int): Result<List<Int>>

    suspend fun loadHistory(offset:Int): Result<List<Int>>

    suspend fun loadAllReadComicIds(offset:Int): Result<List<Int>>

    suspend fun loadAllReadSeriesIds(offset:Int): Result<List<Int>>

    suspend fun loadWillBeReadIds(offset:Int): Result<List<Int>>

    suspend fun loadStatistics(): Result<StatisticsforAll>

    suspend fun loadFavoritesIds(): Result<List<Int>>

    suspend fun loadComicMark(apiId: Int): Result<String>

    suspend fun loadSeriesMark(apiId:Int): Result<String>

    suspend fun loadSeriesFavoriteMark(apiId: Int): Result<Boolean>

    suspend fun loadNextRead(seriesApiId: Int): Result<Int?>

    suspend fun loadFavoritesCount():Result<Int>

}