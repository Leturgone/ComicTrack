package com.example.comictracker.ui.screens.aboutScreens.aboutComic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.comictracker.ui.screens.aboutScreens.AboutCreatorsAndCharactersSec
@Composable
fun  ComicScreen(navController1: Int, navController: NavHostController){
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AboutComicSec(navController)
        UsersComicMarkSec()
        AboutCreatorsAndCharactersSec(emptyList(), emptyList(), navController)
    }
}