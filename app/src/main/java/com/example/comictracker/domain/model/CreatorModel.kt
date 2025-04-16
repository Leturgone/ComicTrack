package com.example.comictracker.domain.model

/**
 * Creator model
 *
 * @property creatorId
 * @property name
 * @property image
 * @property role
 * @constructor Create empty Creator model
 */
data class CreatorModel(
    val creatorId: Int,
    val name:String,
    val image:String,
    val role:String
)
