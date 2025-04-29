package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AboutSeriesScreenIntent
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.ui.screens.aboutScreens.AboutCreatorsAndCharactersSec
import com.example.comictracker.presentation.viewmodel.AboutSeriesScreenViewModel

@Composable
fun SeriesScreen(
    seriesId: Int,
    navController: NavHostController,
    viewModel: AboutSeriesScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = seriesId) {
        viewModel.processIntent(AboutSeriesScreenIntent.LoadSeriesScreen(seriesId))
    }
    uiState.let { state ->
        when(state){
            is ComicAppState.AboutSeriesScreenState -> {
                when(state.dataState){
                    is DataState.Error -> CustomToastMessage(
                        message = state.dataState.errorMessage,
                        isVisible = showToast,
                        onDismiss = { showToast = false })
                    is DataState.Loading -> CircularProgressIndicator()
                    is DataState.Success -> {
                        Column(Modifier.verticalScroll(rememberScrollState())){
                            AboutSeriesSec(state.dataState.result.series!!)

                            val firstIssue = if (state.dataState.result.comicList.isNotEmpty())  state.dataState.result.comicList[0] else null

                            UsersSeriesMarkSec(seriesId,
                                state.dataState.result.series.readMark,
                                state.dataState.result.series.favoriteMark,
                                firstIssue?.comicId)



                            if (state.dataState.result.nextRead!=null) {
                                NextComicSec(state.dataState.result.nextRead, navController)
                            }else{
                                NextComicSec(firstIssue, navController)
                            }

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