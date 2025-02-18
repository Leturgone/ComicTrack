package com.example.comictracker.aboutScreens.aboutComic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.comictracker.aboutScreens.AboutCreatorsAndCharactersSec
@Composable
fun  ComicScreen(navController: NavHostController){
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AboutComicSec()
        UsersComicMarkSec()
        AboutCreatorsAndCharactersSec(navController)
    }
}