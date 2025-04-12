package com.example.comictracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.example.comictracker.data.database.enteties.SeriesEntity

interface SeriesDao {
    @Query("SELECT * FROM series")
    fun getSeries(): LiveData<List<SeriesEntity>>

    @Insert
    fun addSeries(seriesEntity: SeriesEntity)

    @Query("SELECT * FROM series WHERE idSeries=:id")
    fun getSeriesById(id:Int)

    @Query("SELECT mark FROM series WHERE seriesApiId =:apiId")
    fun getSeriesMark(apiId:Int): LiveData<String>

    @Query("UPDATE series SET mark=:mark WHERE seriesApiId=:apiId")
    fun setSeriesMark(apiId: Int,mark:String)

    @Query("DELETE FROM series where seriesApiId=:apiId")
    fun removeSeries(apiId: Int)

}