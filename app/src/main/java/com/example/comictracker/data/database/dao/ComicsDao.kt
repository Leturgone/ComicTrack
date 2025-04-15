package com.example.comictracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comictracker.data.database.enteties.ComicsEntity

@Dao
interface ComicsDao {
    @Query("SELECT * FROM comics")
    fun getComics(): LiveData<List<ComicsEntity>>

    @Query("SELECT COUNT(*) FROM comics WHERE mark = 'read'")
    fun getComicsCount(): Int

    @Query("SELECT * FROM comics WHERE comicApiId=:apiId")
    fun getComicByApiId(apiId:Int): ComicsEntity?

    @Query("SELECT comicApiId FROM comics WHERE mark = 'read'")
    fun getReadComicApiIds():List<Int>

    @Query("SELECT * FROM comics WHERE idComics=:id")
    fun getComicById(id:Int):ComicsEntity

    @Query("SELECT mark FROM comics WHERE comicApiId =:apiId")
    fun getComicMark(apiId:Int): String?

    @Insert
    fun addComic(comicsEntity: ComicsEntity)

    @Query("DELETE FROM comics WHERE comicApiId = :apiId")
    fun removeComic(apiId:Int)

}