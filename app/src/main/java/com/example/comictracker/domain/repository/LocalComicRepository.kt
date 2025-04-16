package com.example.comictracker.domain.repository

import com.example.comictracker.domain.model.StatisticsforAll

/**
 * Local comic repository
 *
 * @constructor Create empty Local comic repository
 */
interface LocalComicRepository {
    /**
     * Load current read ids
     *
     * @param offset
     * @return
     */
    suspend fun loadCurrentReadIds(offset:Int):List<Int>

    /**
     * Load next read comic ids
     *
     * @param offset
     * @return
     */
    suspend fun loadNextReadComicIds(offset:Int):List<Int>


    /**
     * Load history
     *
     * @param offset
     * @return
     */
    suspend fun loadHistory(offset:Int):List<Int>

    /**
     * Load all read comic ids
     *
     * @param offset
     * @return
     */
    suspend fun loadAllReadComicIds(offset:Int):List<Int>

    /**
     * Load all read series ids
     *
     * @param offset
     * @return
     */
    suspend fun loadAllReadSeriesIds(offset:Int):List<Int>

    /**
     * Load will be read ids
     *
     * @param offset
     * @return
     */
    suspend fun loadWillBeReadIds(offset:Int):List<Int>

    /**
     * Load statistics
     *
     * @return
     */
    suspend fun loadStatistics():StatisticsforAll

    /**
     * Load favorites ids
     *
     * @return
     */
    suspend fun loadFavoritesIds():List<Int>

    /**
     * Load comic mark
     *
     * @param apiId
     * @return
     */
    suspend fun loadComicMark(apiId: Int):String

    /**
     * Load series mark
     *
     * @param apiId
     * @return
     */
    suspend fun loadSeriesMark(apiId:Int):String

    /**
     * Load series favorite mark
     *
     * @param apiId
     * @return
     */
    suspend fun loadSeriesFavoriteMark(apiId: Int):Boolean

    /**
     * Mark series read
     *
     * @param apiId
     * @return
     */
    suspend fun markSeriesRead(apiId: Int):Boolean

    /**
     * Mark comic read
     *
     * @param apiId
     * @param seriesApiId
     * @param nextComicApiId
     * @return
     */
    suspend fun markComicRead(apiId: Int, seriesApiId:Int, nextComicApiId:Int?):Boolean

    /**
     * Add series to favorite
     *
     * @param apiId
     * @return
     */
    suspend fun addSeriesToFavorite(apiId: Int):Boolean

    /**
     * Remove series from favorite
     *
     * @param apiId
     * @return
     */
    suspend fun removeSeriesFromFavorite(apiId: Int):Boolean

    /**
     * Add series to currently read
     *
     * @param apiId
     * @param firstIssueId
     * @return
     */
    suspend fun addSeriesToCurrentlyRead(apiId: Int, firstIssueId:Int?):Boolean

    /**
     * Add series to will be read
     *
     * @param apiId
     * @return
     */
    suspend fun addSeriesToWillBeRead(apiId: Int):Boolean

    /**
     * Mark comic unread
     *
     * @param apiId
     * @param seriesApiId
     * @param prevComicApiId
     * @return
     */
    suspend fun markComicUnread(apiId: Int, seriesApiId:Int, prevComicApiId:Int?):Boolean

    /**
     * Mark series unread
     *
     * @param apiId
     * @return
     */
    suspend fun markSeriesUnread(apiId: Int):Boolean

    /**
     * Load next read
     *
     * @param apiId
     * @return
     */
    suspend fun loadNextRead(apiId: Int):Int?

}