package com.example.comictracker.presentation.mvi.intents

sealed class HomeScreenIntent {
    data object LoadHomeScreen: HomeScreenIntent()

}