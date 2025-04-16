package com.example.comictracker.domain.model

/**
 * Character model
 *
 * @property characterId
 * @property name
 * @property desc
 * @property image
 * @property series
 * @constructor Create empty Character model
 */
data class CharacterModel(
    val characterId:Int,
    val name:String,
    val desc:String,
    val image:String,
    val series: List<Int>
)
