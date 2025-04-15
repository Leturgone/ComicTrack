package com.example.comictracker.data.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SeriesListDao {

    @Query("UPDATE series_list SET listType = 'currently' WHERE Series_idSeries =:seriesId")
    fun addToCurrentlyReading(seriesId:Int)

    @Query("UPDATE series_list SET listType = 'will' WHERE Series_idSeries =:seriesId")
    fun addToWillBeRead(seriesId: Int)

    @Query("INSERT INTO series_list (listType, Series_idSeries, favorite) VALUES('read', :seriesId, 0)")
    fun addToRead(seriesId: Int)

    @Query("UPDATE series_list SET listType = 'read' WHERE Series_idSeries =:seriesId")
    fun addToReadUpdate(seriesId: Int)

    @Query("UPDATE series_list SET favorite = 'true' WHERE Series_idSeries =:seriesId ")
    fun addToFavorites(seriesId:Int)

    @Query("DELETE FROM series_list  WHERE Series_idSeries =:seriesId ")
    fun removeFromLists(seriesId:Int)

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently'")
    fun getCurrentlyReadingSeriesApiIds(): List<Int>

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'will'")
    fun getWillBeReadSeriesApiIds(): List<Int>

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'read'")
    fun getReadSeriesApiIds(): List<Int>

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.favorite = 'true' ")
    fun getFavoriteSeriesApiIds(): List<Int>

    @Query("SELECT COUNT(*) FROM series_list WHERE listType = 'read'")
    fun getReadSeriesCount(): Int

    @Query("SELECT COUNT(*) FROM series_list WHERE listType = 'will'")
    fun getWillBeReadSeriesCount(): Int


    @Query("SELECT EXISTS (SELECT 1 FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE se.seriesApiId = :apiId)")
    fun isSeriesInList(apiId: Int): Boolean

    @Query("SELECT sl.listType FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE se.seriesApiId = :apiId")
    fun getSeriesMark(apiId: Int): String?

    @Query("SELECT se.nextReadId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently'")
    fun getNextComicsForSeries():List<Int?>

    @Query("SELECT se.lastReadId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently'")
    fun getLastReadComicsForSeries():List<Int?>



}