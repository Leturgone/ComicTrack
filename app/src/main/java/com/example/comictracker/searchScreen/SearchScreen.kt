package com.example.comictracker.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SearchScreen(){
    Column {
        SearchSec()
        Column(Modifier.verticalScroll(rememberScrollState())) {
            MayLikeSec()
            DiscoverSec()
            CharacterSec()
        }
    }



}