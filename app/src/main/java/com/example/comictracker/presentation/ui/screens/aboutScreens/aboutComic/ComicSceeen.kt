package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutComic

import androidx.compose.foundation.layout.Box
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
import com.example.comictracker.presentation.mvi.intents.AboutComicScreenIntent
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.ui.screens.aboutScreens.AboutCreatorsAndCharactersSec
import com.example.comictracker.presentation.viewmodel.AboutComicScreenViewModel

@Composable
fun  ComicScreen(comicId: Int,
                 navController: NavHostController,
                 viewModel: AboutComicScreenViewModel = hiltViewModel()){

    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = comicId) {
        viewModel.processIntent(AboutComicScreenIntent.LoadComicScreen(comicId))
    }
    Box {
        uiState.let {state ->
            when(state){
                is ComicAppState.AboutComicScreenState ->{
                    when(state.dataState){
                        is DataState.Error -> LaunchedEffect(Unit){
                            errorMessage = state.dataState.errorMessage
                            showToast = true
                        }
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
        Column {
            CustomToastMessage(
                message = errorMessage,
                isVisible = showToast,
                onDismiss = { showToast = false })
        }
    }

}