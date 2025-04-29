package com.example.comictracker.presentation.mvi.intents

sealed class AboutCharacterScreenIntent {
    data class LoadCharacterScreen(val characterId:Int): AboutCharacterScreenIntent()

}