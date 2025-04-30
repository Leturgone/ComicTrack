package com.example.comictracker.presentation.mvi.intents

sealed class AllScreenIntent {
    data class LoadAllScreen(val sourceId: Int, val sourceCat:String, val loadedCount: Int ): AllScreenIntent()

    data class LoadAllCharactersScreen(val loadedCount: Int ): AllScreenIntent()
}