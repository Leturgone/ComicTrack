package com.example.comictracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comictracker.data.database.enteties.ComicsEntity

/**
 * Comics dao
 *
 * @constructor Create empty Comics dao
 */
@Dao
interface ComicsDao {
    /**
     * Get comics
     *
     * @return
     */
    @Query("SELECT * FROM comics")
    fun getComics(): LiveData<List<ComicsEntity>>

    /**
     * Get history
     *
     * @param offset
     * @return
     */
    @Query("SELECT comicApiId FROM comics ORDER BY idComics DESC LIMIT 10 OFFSET :offset")
    fun getHistory(offset:Int): List<Int?>

    /**
     * Get comics count
     *
     * @return
     */
    @Query("SELECT COUNT(*) FROM comics WHERE mark = 'read'")
    fun getComicsCount(): Int

    /**
     * Get comic by api id
     *
     * @param apiId
     * @return
     */
    @Query("SELECT * FROM comics WHERE comicApiId=:apiId")
    fun getComicByApiId(apiId:Int): ComicsEntity?

    /**
     * Get read comic api ids
     *
     * @param offset
     * @return
     */
    @Query("SELECT comicApiId FROM comics WHERE mark = 'read' LIMIT 10 OFFSET :offset")
    fun getReadComicApiIds(offset:Int):List<Int>

    /**
     * Get comic by id
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM comics WHERE idComics=:id")
    fun getComicById(id:Int):ComicsEntity

    /**
     * Get comic mark
     *
     * @param apiId
     * @return
     */
    @Query("SELECT mark FROM comics WHERE comicApiId =:apiId")
    fun getComicMark(apiId:Int): String?

    /**
     * Add comic
     *
     * @param comicsEntity
     */
    @Insert
    fun addComic(comicsEntity: ComicsEntity)

    /**
     * Remove comic
     *
     * @param apiId
     */
    @Query("DELETE FROM comics WHERE comicApiId = :apiId")
    fun removeComic(apiId:Int)

}