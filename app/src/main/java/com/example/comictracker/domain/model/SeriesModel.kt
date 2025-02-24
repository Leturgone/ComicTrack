package com.example.comictracker.domain.model

data class SeriesModel(
    val id: Int,
    val title:String,
    val date:String,
    val desc:String,
    val comics: List<ComicModel>,
    val creators: List<CreatorModel>,
    val characters: List<CharacterModel>,
    val connectedSeries: List<SeriesModel>,
    val readMark:String
)
