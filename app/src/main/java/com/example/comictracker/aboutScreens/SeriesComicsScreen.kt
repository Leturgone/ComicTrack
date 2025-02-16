package com.example.comictracker.aboutScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SeriesScreen(){
    Column(Modifier.verticalScroll(rememberScrollState())){
        AboutSeriesSec()
        UsersMarkSec()
        NextComicSec()
        AboutCreatorsAndCharactersSec()
        RelatedSec()
    }

}