package com.example.comictracker.ui.screens.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun SearchScreen(navController: NavHostController){
    Column {
        SearchSec(navController)
        Column(Modifier.verticalScroll(rememberScrollState())) {
            MayLikeSec(navController)
            DiscoverSec(navController)
            CharacterSec(navController)
        }
    }



}