package com.example.comictracker.presentation.mvi.intents

sealed class LibraryScreenIntent {
    data object LoadLibraryScreen: LibraryScreenIntent()

}