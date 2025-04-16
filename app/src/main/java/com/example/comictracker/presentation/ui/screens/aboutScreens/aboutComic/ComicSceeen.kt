package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutComic

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
import com.example.comictracker.presentation.mvi.ComicAppIntent
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.ui.screens.aboutScreens.AboutCreatorsAndCharactersSec
import com.example.comictracker.presentation.viewmodel.ComicViewModel

@Composable
fun  ComicScreen(comicId: Int,
                 navController: NavHostController,
                 viewModel: ComicViewModel = hiltViewModel()){

    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = comicId) {
        viewModel.processIntent(ComicAppIntent.LoadComicScreen(comicId))
    }

    uiState.let {state ->
        when(state){
            is ComicAppState.AboutComicScreenState ->{
                when(state.dataState){
                    is DataState.Error -> CustomToastMessage(
                        message = state.dataState.errorMessage,
                        isVisible = showToast,
                        onDismiss = { showToast = false })
                    DataState.Loading -> CircularProgressIndicator()
                    is DataState.Success -> {
                        Column(Modifier.verticalScroll(rememberScrollState())) {
                            AboutComicSec(state.dataState.result.comic!!,navController)
                            UsersComicMarkSec(comicId,
                                state.dataState.result.comic.readMark,
                                state.dataState.result.comic.seriesId,
                                state.dataState.result.comic.number)
                            AboutCreatorsAndCharactersSec(state.dataState.result.creatorList,
                                state.dataState.result.characterList, navController)
                        }
                    }
                }
            }

            else -> {CircularProgressIndicator()}
        }
    }
    
}