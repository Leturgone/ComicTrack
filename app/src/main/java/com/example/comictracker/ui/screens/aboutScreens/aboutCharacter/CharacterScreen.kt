package com.example.comictracker.ui.screens.aboutScreens.aboutCharacter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.comictracker.ui.screens.AllComicScreen


@Composable
fun CharacterScreen(navController: NavHostController){
    Column() {
        CharacterSec()
        AllComicScreen(navController)
    }
}