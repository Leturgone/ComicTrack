package com.example.comictracker.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun SearchScreen(navController: NavHostController){
    Column {
        SearchSec()
        Column(Modifier.verticalScroll(rememberScrollState())) {
            MayLikeSec()
            DiscoverSec()
            CharacterSec()
        }
    }



}