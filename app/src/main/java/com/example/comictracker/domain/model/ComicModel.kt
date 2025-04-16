package com.example.comictracker.domain.model

/**
 * Comic model
 *
 * @property comicId
 * @property title
 * @property number
 * @property image
 * @property seriesId
 * @property seriesTitle
 * @property date
 * @property creators
 * @property characters
 * @property readMark
 * @constructor Create empty Comic model
 */
data class ComicModel(
    val comicId :Int,
    val title: String,
    val number:String,
    val image:String,
    val seriesId:Int,
    val seriesTitle:String,
    val date: String,
    val creators: List<Pair<Int,String>>,
    val characters: List<Int>,
    val readMark:String
)