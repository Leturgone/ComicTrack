package com.example.comictracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comictracker.data.database.enteties.SeriesEntity

/**
 * Series dao
 *
 * @constructor Create empty Series dao
 */
@Dao
interface SeriesDao {
    /**
     * Get series
     *
     * @return
     */
    @Query("SELECT * FROM series")
    fun getSeries(): LiveData<List<SeriesEntity>>

    /**
     * Add series
     *
     * @param seriesEntity
     */
    @Insert
    fun addSeries(seriesEntity: SeriesEntity)

    /**
     * Get series by id
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM series WHERE idSeries=:id")
    fun getSeriesById(id:Int):SeriesEntity

    /**
     * Get series by api id
     *
     * @param apiId
     * @return
     */
    @Query("SELECT * FROM series WHERE seriesApiId=:apiId")
    fun getSeriesByApiId(apiId:Int):SeriesEntity?

    /**
     * Remove series
     *
     * @param apiId
     */
    @Query("DELETE FROM series where seriesApiId=:apiId")
    fun removeSeries(apiId: Int)

    /**
     * Get last read
     *
     * @param apiId
     * @return
     */
    @Query("SELECT lastReadId FROM series WHERE seriesApiId=:apiId")
    fun getLastRead(apiId: Int):Int?

    /**
     * Get next read
     *
     * @param apiId
     * @return
     */
    @Query("SELECT nextReadId FROM series WHERE seriesApiId=:apiId")
    fun getNextRead(apiId:Int):Int?

    /**
     * Set last read
     *
     * @param apiId
     * @param lastReadId
     */
    @Query("UPDATE series SET lastReadId=:lastReadId WHERE seriesApiId=:apiId")
    fun setLastRead(apiId:Int,lastReadId:Int?)

    /**
     * Set next read
     *
     * @param apiId
     * @param nextReadId
     */
    @Query("UPDATE series SET nextReadId=:nextReadId WHERE seriesApiId=:apiId")
    fun setNextRead(apiId:Int,nextReadId:Int?)



}