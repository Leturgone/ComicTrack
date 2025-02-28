package com.example.comictracker.domain.model

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