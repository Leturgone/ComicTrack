package com.example.comictracker.aboutScreens.aboutComic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.comictracker.aboutScreens.AboutCreatorsAndCharactersSec
@Preview
@Composable
fun  ComicScreen(){
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AboutComicSec()
        UsersComicMarkSec()
        AboutCreatorsAndCharactersSec()
    }
}