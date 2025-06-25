package com.example.comictracker.presentation.ui.screens.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.SearchScreenIntent
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.viewmodel.SearchScreenViewModel


@Composable
fun SearchScreen(navController: NavHostController,
                 viewModel: SearchScreenViewModel = hiltViewModel()){

    val uiState by viewModel.state.collectAsState()
    var showMayLikeToast by remember { mutableStateOf(false) }
    var mayLikeErrorMessage by remember { mutableStateOf("") }
    var showDiscoverToast by remember { mutableStateOf(false) }
    var discoverErrorMessage by remember { mutableStateOf("") }
    var showCharacterToast by remember { mutableStateOf(false) }
    var characterErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = "searchscreen") {
        viewModel.processIntent(SearchScreenIntent.LoadSearchScreen)
    }
    Box(){
        Column {
            SearchSec(navController)
            Column(Modifier.verticalScroll(rememberScrollState())) {
                uiState.let {state ->
                    when(state){
                        is ComicAppState.SearchScreenState ->{
                            when(state.mayLikeList){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    mayLikeErrorMessage = state.mayLikeList.errorMessage
                                    showMayLikeToast = true
                                }
                                DataState.Loading -> CircularProgressIndicator()
                                is DataState.Success -> MayLikeSec(state.mayLikeList.result,navController)
                            }
                            when(state.discoverList){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    discoverErrorMessage = state.discoverList.errorMessage
                                    showDiscoverToast = true
                                }
                                DataState.Loading -> CircularProgressIndicator()
                                is DataState.Success -> DiscoverSec(state.discoverList.result,navController)
                            }
                            when(state.charactersSec){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    characterErrorMessage = state.charactersSec.errorMessage
                                    showCharacterToast = true
                                }
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
        Column {
            CustomToastMessage(
                message = mayLikeErrorMessage,
                isVisible = showMayLikeToast,
                onDismiss = { showMayLikeToast = false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = discoverErrorMessage,
                isVisible = showDiscoverToast,
                onDismiss = { showDiscoverToast = false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = characterErrorMessage,
                isVisible = showCharacterToast,
                onDismiss = { showCharacterToast = false })
        }
    }
}