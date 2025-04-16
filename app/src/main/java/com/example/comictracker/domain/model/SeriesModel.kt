package com.example.comictracker.domain.model

/**
 * Series model
 *
 * @property seriesId
 * @property title
 * @property date
 * @property desc
 * @property image
 * @property comics
 * @property creators
 * @property characters
 * @property connectedSeries
 * @property readMark
 * @property favoriteMark
 * @constructor Create empty Series model
 */
data class SeriesModel(
    val seriesId: Int,
    val title:String?,
    val date:String?,
    val desc:String?,
    val image:String?,
    val comics: List<Int>?,
    val creators: List<Pair<Int, String>>?,
    val characters: List<Int>?,
    val connectedSeries: List<Int?>,
    val readMark:String,
    val favoriteMark:Boolean
)
