package com.example.comictracker.domain.model

data class CharacterModel(
    val characterId:Int,
    val name:String,
    val desc:String,
    val image:String,
    val series: List<Int>
)
