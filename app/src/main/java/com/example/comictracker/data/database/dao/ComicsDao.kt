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

    @Query("SELECT * FROM comics WHERE idComics=:id")
    fun getComicById(id:Int)

    @Query("SELECT mark FROM comics WHERE comicApiId =:apiId")
    fun getComicMark(apiId:Int): LiveData<String>

    @Insert
    fun addComic(comicsEntity: ComicsEntity)

    @Query("DELETE FROM comics WHERE comicApiId = :apiId")
    fun removeComic(apiId:Int)

}