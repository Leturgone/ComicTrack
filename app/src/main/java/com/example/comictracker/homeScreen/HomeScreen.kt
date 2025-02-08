package com.example.comictracker.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(){
    Column {
        NewComicListSec()
        Spacer(modifier = Modifier.height(12.dp))
        CurrentReadComicListSec()
    }
}