package com.example.comictracker.ui.screens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController){
    Column {
        NewComicListSec(navController)
        Spacer(modifier = Modifier.height(12.dp))
        CurrentReadComicListSec(navController)
    }
}