package com.example.comictracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comictracker.data.database.enteties.SeriesEntity

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series")
    fun getSeries(): LiveData<List<SeriesEntity>>

    @Insert
    fun addSeries(seriesEntity: SeriesEntity)

    @Query("SELECT * FROM series WHERE idSeries=:id")
    fun getSeriesById(id:Int):SeriesEntity

    @Query("SELECT * FROM series WHERE seriesApiId=:apiId")
    fun getSeriesByApiId(apiId:Int):SeriesEntity?

    @Query("DELETE FROM series where seriesApiId=:apiId")
    fun removeSeries(apiId: Int)

    @Query("SELECT lastReadId FROM series WHERE seriesApiId=:apiId")
    fun getLastRead(apiId: Int):Int

    @Query("SELECT nextReadId FROM series WHERE seriesApiId=:apiId")
    fun getNextRead(apiId:Int):Int

    @Query("UPDATE series SET lastReadId=:lastReadId WHERE seriesApiId=:apiId")
    fun setLastRead(apiId:Int,lastReadId:Int)

    @Query("UPDATE series SET nextReadId=:nextReadId WHERE seriesApiId=:apiId")
    fun setNextRead(apiId:Int,nextReadId:Int)



}