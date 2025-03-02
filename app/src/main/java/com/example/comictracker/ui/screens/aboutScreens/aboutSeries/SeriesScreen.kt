package com.example.comictracker.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.mvi.ComicAppIntent
import com.example.comictracker.mvi.ComicAppState
import com.example.comictracker.mvi.DataState
import com.example.comictracker.ui.screens.aboutScreens.AboutCreatorsAndCharactersSec
import com.example.comictracker.viewmodel.ComicViewModel

@Composable
fun SeriesScreen(
    seriesId: Int,
    navController: NavHostController, viewModel: ComicViewModel = hiltViewModel()){
    val uiState by viewModel.state.collectAsState()


    LaunchedEffect(key1 = seriesId) {
        viewModel.processIntent(ComicAppIntent.LoadSeriesScreen(seriesId))
    }
    uiState.let { state ->
        when(state){
            is ComicAppState.AboutSeriesScreenState -> {
                when(state.dataState){
                    is DataState.Error -> TODO()
                    is DataState.Loading -> CircularProgressIndicator()
                    is DataState.Success -> {
                        Column(Modifier.verticalScroll(rememberScrollState())){
                            AboutSeriesSec(state.dataState.result.series!!)
                            UsersSeriesMarkSec(seriesId)
                            NextComicSec(state.dataState.result.comicList,navController)
                            AboutCreatorsAndCharactersSec(
                                state.dataState.result.creatorList,
                                state.dataState.result.characterList,
                                navController)
                            RelatedSec(state.dataState.result.connectedSeriesList,navController)
                        }
                    }
                }
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }


}