package com.example.comictracker.domain.model

data class ComicModel(
    val comicId :Int,
    val title: String,
    val seriesId:Int,
    val seriesTitle:String,
    val date: String,
    val creators: List<CreatorModel>,
    val characters: List<CharacterModel>,
    val readMark:String
)