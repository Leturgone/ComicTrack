package com.example.comictracker.data.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SeriesListDao {

    @Query("UPDATE series_list SET listType = 'currently' WHERE Series_idSeries =:seriesId")
    fun addToCurrentlyReading(seriesId:Int)


    @Query("SELECT EXISTS(SELECT 1 FROM series_list  WHERE listType = 'currently' AND Series_idSeries = :seriesId LIMIT 1)")
    fun checkAlreadyCurrentlyRead(seriesId: Int):Boolean

    @Query("UPDATE series_list SET listType = 'will' WHERE Series_idSeries =:seriesId")
    fun addToWillBeRead(seriesId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM series_list WHERE listType = 'will' AND Series_idSeries = :seriesId)")
    fun checkAlreadyWillBeRead(seriesId: Int):Boolean

    @Query("INSERT INTO series_list (listType, Series_idSeries, favorite) VALUES('read', :seriesId, 0)")
    fun addToRead(seriesId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM series_list WHERE listType = 'read' AND Series_idSeries = :seriesId)")
    fun checkAlreadyRead(seriesId: Int):Boolean

    @Query("UPDATE series_list SET listType = 'read' WHERE Series_idSeries =:seriesId")
    fun addToReadUpdate(seriesId: Int)

    @Query("SELECT COUNT(*) FROM series_list WHERE favorite = 1")
    fun getFavoritesCount(): Int

    @Query("UPDATE series_list SET favorite = 1 WHERE Series_idSeries =:seriesId ")
    fun addToFavorites(seriesId:Int)

    @Query("UPDATE series_list SET favorite = 0 WHERE Series_idSeries =:seriesId ")
    fun removeFromFavorites(seriesId:Int)

    @Query("DELETE FROM series_list  WHERE Series_idSeries =:seriesId ")
    fun removeFromLists(seriesId:Int)

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently' LIMIT 10 OFFSET :offset")
    fun getCurrentlyReadingSeriesApiIds(offset:Int): List<Int>

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'will' LIMIT 10 OFFSET :offset")
    fun getWillBeReadSeriesApiIds(offset: Int): List<Int>

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'read' LIMIT 10 OFFSET :offset")
    fun getReadSeriesApiIds(offset: Int): List<Int>

    @Query("SELECT se.seriesApiId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.favorite = 1 LIMIT 5")
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

    @Query("SELECT sl.favorite FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE se.seriesApiId = :apiId")
    fun getSeriesFavoriteMark(apiId: Int): Boolean

    @Query("SELECT se.nextReadId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently' LIMIT 10 OFFSET :offset")
    fun getNextComicsForSeries(offset: Int):List<Int?>

    @Query("SELECT se.lastReadId FROM series_list sl " +
            "JOIN series se ON sl.Series_idSeries = se.idSeries " +
            "WHERE sl.listType = 'currently'")
    fun getLastReadComicsForSeries():List<Int?>



}