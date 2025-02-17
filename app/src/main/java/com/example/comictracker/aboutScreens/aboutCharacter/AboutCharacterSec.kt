package com.example.comictracker.aboutScreens.aboutCharacter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.comictracker.AllComicScreen


@Composable
fun AboutCharacterScreen(){
    Column(Modifier.verticalScroll(rememberScrollState())) {
        CharacterSec()
        AllComicScreen()
    }
}