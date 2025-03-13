package com.example.comictracker.data.database.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "comics",
    foreignKeys = [
        ForeignKey(
            entity = SeriesEntity::class,
            parentColumns = ["idComics"],
            childColumns = ["Series_idSeries"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class ComicsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idComics")
    var id: Int = 0
    @ColumnInfo(name = "comicApiId")
    var apiId: Int = 0
    @ColumnInfo(name = "mark")
    var mark: String = "unread"
    @ColumnInfo(name = "Series_idSeries")
    var Series_idSeries: Int = 0
}