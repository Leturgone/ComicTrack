package com.example.comictracker.presentation.mvi.intents

import com.example.comictracker.presentation.mvi.ComicAppIntent

sealed class AllScreenIntent {
    data class LoadAllScreen(val sourceId: Int, val sourceCat:String, val loadedCount: Int ): AllScreenIntent()

    data class LoadAllCharactersScreen(val loadedCount: Int ): AllScreenIntent()
}