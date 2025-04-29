package com.example.comictracker.presentation.mvi.intents

sealed class SearchScreenIntent {
    data object LoadSearchScreen: SearchScreenIntent()
    data class Search(val query:String): SearchScreenIntent()
}