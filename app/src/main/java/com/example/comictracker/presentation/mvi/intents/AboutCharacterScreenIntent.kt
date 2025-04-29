package com.example.comictracker.presentation.mvi.intents

import com.example.comictracker.presentation.mvi.ComicAppIntent

sealed class AboutCharacterScreenIntent {
    data class LoadCharacterScreen(val characterId:Int): AboutCharacterScreenIntent()

}