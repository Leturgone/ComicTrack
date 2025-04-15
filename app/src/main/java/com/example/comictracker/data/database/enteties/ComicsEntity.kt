package com.example.comictracker.data.database.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "comics",
    foreignKeys = [
        ForeignKey(
            entity = SeriesEntity::class,
            parentColumns = ["idSeries"],
            childColumns = ["Series_idSeries"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["idComics", "comicApiId"], unique = true)]
)
data class ComicsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idComics")
    var id: Int = 0,
    @ColumnInfo(name = "comicApiId")
    var comicApiId: Int = 0,
    @ColumnInfo(name = "mark")
    var mark: String = "unread",
    @ColumnInfo(name = "Series_idSeries")
    var Series_idSeries: Int = 0
)