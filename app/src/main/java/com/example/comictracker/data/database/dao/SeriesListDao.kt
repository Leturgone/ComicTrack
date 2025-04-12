package com.example.comictracker.data.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SeriesListDao {

    @Query("INSERT INTO series_list (listType,Series_idSeries) VALUES('currently',:seriesId) ")
    fun addToCurrentlyReading(seriesId:Int)

    @Query("INSERT INTO series_list (listType,Series_idSeries) VALUES('will',:seriesId) ")
    fun addToWillBeRead(seriesId: Int)

    @Query("INSERT INTO series_list (listType,Series_idSeries) VALUES('read',:seriesId) ")
    fun addToRead(seriesId: Int)

    @Query("UPDATE series_list SET favorite = 'true' WHERE Series_idSeries =:seriesId ")
    fun addToFavorites(seriesId:Int)

    @Query("DELETE FROM series_list  WHERE Series_idSeries =:seriesId ")
    fun removeFromLists(seriesId:Int)


}