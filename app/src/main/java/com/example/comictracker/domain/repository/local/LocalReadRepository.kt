package com.example.comictracker.domain.repository.local

import com.example.comictracker.domain.model.StatisticsforAll

interface LocalReadRepository {
    suspend fun loadCurrentReadIds(offset:Int):List<Int>

    suspend fun loadNextReadComicIds(offset:Int):List<Int>


    suspend fun loadHistory(offset:Int):List<Int>

    suspend fun loadAllReadComicIds(offset:Int):List<Int>

    suspend fun loadAllReadSeriesIds(offset:Int):List<Int>

    suspend fun loadWillBeReadIds(offset:Int):List<Int>

    suspend fun loadStatistics(): StatisticsforAll

    suspend fun loadFavoritesIds():List<Int>

    suspend fun loadComicMark(apiId: Int):String

    suspend fun loadSeriesMark(apiId:Int):String

    suspend fun loadSeriesFavoriteMark(apiId: Int):Boolean

    suspend fun loadNextRead(seriesApiId: Int):Int?

}