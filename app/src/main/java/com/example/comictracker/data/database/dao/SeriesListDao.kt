package com.example.comictracker.data.database.dao

import androidx.room.Dao
import androidx.room.Query

/**
 * Series list dao
 *
 * @constructor Create empty Series list dao
 */
@Dao
interface SeriesListDao {

    /**
     * Add to currently reading
     *
     * @param seriesId
     */
    @Query("UPDATE series_list SET listType = 'currently' WHERE Series_idSeries =:seriesId")
    fun addToCurrentlyReading(seriesId:Int)

    /**
     * Add to will be read
     *
     * @param seriesId
     */
    @Query("UPDATE series_list SET listType = 'will' WHERE Series_idSeries =:seriesId")
    fun addToWillBeRead(seriesId: Int)

    /**
     * Add to read
     *
     * @param seriesId
     */
    @Query("INSERT INTO series_list (listType, Series_idSeries, favorite) VALUES('read', :seriesId, 0)")
    fun addToRead(seriesId: Int)

    /**
     * Add to read update
     *
     * @param seriesId
     */
    @Query("UPDATE series_list SET listType = 'read' WHERE Series_idSeries =:seriesId")
    fun addToReadUpdate(seriesId: Int)

    /**
     * Add to favorites
     *
     * @param seriesId
     */
    @Query("UPDATE series_list SET favorite = 1 WHERE Series_idSeries =:seriesId ")
    fun addToFavorites(seriesId:Int)

    /**
     * Remove from favorites
     *
     * @param seriesId
     */
    @Query("UPDATE series_list SET favorite = 0 WHERE Series_idSeries =:seriesId ")
    fun removeFromFavorites(seriesId:Int)

    /**
     * Remove from lists
     *
     * @param seriesId
     */
    @Query("DELETE FROM series_list  WHERE Series_idSeries =:seriesId ")
    fun removeFromLists(seriesId:Int)

    /**
     * Get currently reading series api ids
     *
     * @param offset
     * @return
     */
    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently' LIMIT 10 OFFSET :offset")
    fun getCurrentlyReadingSeriesApiIds(offset:Int): List<Int>

    /**
     * Get will be read series api ids
     *
     * @param offset
     * @return
     */
    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'will' LIMIT 10 OFFSET :offset")
    fun getWillBeReadSeriesApiIds(offset: Int): List<Int>

    /**
     * Get read series api ids
     *
     * @param offset
     * @return
     */
    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'read' LIMIT 10 OFFSET :offset")
    fun getReadSeriesApiIds(offset: Int): List<Int>

    /**
     * Get favorite series api ids
     *
     * @return
     */
    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.favorite = 1 LIMIT 5")
    fun getFavoriteSeriesApiIds(): List<Int>

    /**
     * Get read series count
     *
     * @return
     */
    @Query("SELECT COUNT(*) FROM series_list WHERE listType = 'read'")
    fun getReadSeriesCount(): Int

    /**
     * Get will be read series count
     *
     * @return
     */
    @Query("SELECT COUNT(*) FROM series_list WHERE listType = 'will'")
    fun getWillBeReadSeriesCount(): Int


    /**
     * Is series in list
     *
     * @param apiId
     * @return
     */
    @Query("SELECT EXISTS (SELECT 1 FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE se.seriesApiId = :apiId)")
    fun isSeriesInList(apiId: Int): Boolean

    /**
     * Get series mark
     *
     * @param apiId
     * @return
     */
    @Query("SELECT sl.listType FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE se.seriesApiId = :apiId")
    fun getSeriesMark(apiId: Int): String?

    /**
     * Get series favorite mark
     *
     * @param apiId
     * @return
     */
    @Query("SELECT sl.favorite FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE se.seriesApiId = :apiId")
    fun getSeriesFavoriteMark(apiId: Int): Boolean

    /**
     * Get next comics for series
     *
     * @param offset
     * @return
     */
    @Query("SELECT se.nextReadId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently' LIMIT 10 OFFSET :offset")
    fun getNextComicsForSeries(offset: Int):List<Int?>

    /**
     * Get last read comics for series
     *
     * @return
     */
    @Query("SELECT se.lastReadId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently'")
    fun getLastReadComicsForSeries():List<Int?>



}