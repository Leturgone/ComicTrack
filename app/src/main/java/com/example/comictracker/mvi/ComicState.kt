package com.example.comictracker.mvi

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel

sealed class ComicState {
    data object Idle: ComicState()
    data object Loading: ComicState()
    data class Error(val errorMessage:String): ComicState()
    data class Success<out R>(val result:R): ComicState()

}