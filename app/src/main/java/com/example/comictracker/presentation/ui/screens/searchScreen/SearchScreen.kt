package com.example.comictracker.presentation.ui.screens.searchScreen

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
import com.example.comictracker.presentation.viewmodel.ComicViewModel


@Composable
fun SearchScreen(navController: NavHostController,
                 viewModel: ComicViewModel = hiltViewModel()){

    val uiState by viewModel.state.collectAsState()
    var showMayLikeToast by remember { mutableStateOf(false) }
    var showDiscoverToast by remember { mutableStateOf(false) }
    var showCharacterToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = "searchscreen") {
        viewModel.processIntent(ComicAppIntent.LoadSearchScreen)
    }

    Column {
        SearchSec(navController)
        Column(Modifier.verticalScroll(rememberScrollState())) {
            uiState.let {state -> 
                when(state){
                    is ComicAppState.SearchScreenState ->{
                        when(state.mayLikeList){
                            is DataState.Error -> CustomToastMessage(
                                message = state.mayLikeList.errorMessage,
                                isVisible = showMayLikeToast,
                                onDismiss = { showMayLikeToast = false })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> MayLikeSec(state.mayLikeList.result,navController)
                        }
                        when(state.discoverList){
                            is DataState.Error -> CustomToastMessage(
                                message = state.discoverList.errorMessage,
                                isVisible = showDiscoverToast,
                                onDismiss = { showDiscoverToast = false })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> DiscoverSec(state.discoverList.result,navController)
                        }
                        when(state.charactersSec){
                            is DataState.Error -> CustomToastMessage(
                                message = state.charactersSec.errorMessage,
                                isVisible = showCharacterToast,
                                onDismiss = { showCharacterToast = false })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> CharacterSec(state.charactersSec.result,navController)
                        }
                    }
                    else -> {
                        CircularProgressIndicator()}
                }
            }
        }
    }
}