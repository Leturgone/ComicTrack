package com.example.comictracker.data.database.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "series_list",
    foreignKeys = [
        ForeignKey(
            entity = SeriesEntity::class,
            parentColumns = ["idSeries"],
            childColumns = ["Series_idSeries"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ])
class SeriesList {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("SeriesListItemId")
    var id: Int = 0
    @ColumnInfo("listType")
    var listType: String = ""
    @ColumnInfo("favorite")
    var favorite: Boolean = false
    @ColumnInfo(name = "Series_idSeries")
    var Series_idSeries:Int = 0

}