package com.example.comictracker.data.database.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SeriesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("idSeries")
    var id:Int = 0,
    @ColumnInfo("seriesApiId")
    var seriesApiId:Int = 0,
    @ColumnInfo("lastReadId")
    var lastReadId:Int? = null,
    @ColumnInfo("nextReadId")
    var nextReadId:Int? = null
)