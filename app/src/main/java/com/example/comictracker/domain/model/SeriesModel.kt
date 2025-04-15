package com.example.comictracker.domain.model

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
    val readMark:String
)
