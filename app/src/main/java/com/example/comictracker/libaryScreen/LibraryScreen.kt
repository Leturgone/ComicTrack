package com.example.comictracker.libaryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun LibraryScreen(){
    Column {
        FavoriteSec()
        CurrentReadingSec()
        LatestReadingSec()
    }
}