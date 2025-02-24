package com.example.comictracker.domain.model

data class CharacterModel(
    val id:Int,
    val name:String,
    val desc:String,
    val comics: List<ComicModel>
)
