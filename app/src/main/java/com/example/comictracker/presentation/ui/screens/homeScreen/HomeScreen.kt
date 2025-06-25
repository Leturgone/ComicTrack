package com.example.comictracker.presentation.ui.screens.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.example.comictracker.presentation.mvi.intents.HomeScreenIntent
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(navController: NavHostController,viewModel: HomeScreenViewModel = hiltViewModel()){
    val uiState by viewModel.state.collectAsState()
    var newReleasesShowToast by remember { mutableStateOf(false) }
    var newReleasesErrorMessage by remember { mutableStateOf("") }
    var continueReadingLShowToast by remember { mutableStateOf(false) }
    var continueReadingErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = "homeScreen") {
        viewModel.processIntent(HomeScreenIntent.LoadHomeScreen)
    }
    Box {
        Column {
            CustomToastMessage(
                message = newReleasesErrorMessage,
                isVisible = newReleasesShowToast,
                onDismiss = { newReleasesShowToast = false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = continueReadingErrorMessage,
                isVisible = continueReadingLShowToast,
                onDismiss = { continueReadingLShowToast= false })
        }
        Column {
            uiState.let {state->
                when(state){
                    is ComicAppState.HomeScreenState ->{
                        when(state.newReleasesList){
                            is DataState.Error -> LaunchedEffect(Unit){
                                newReleasesErrorMessage = state.newReleasesList.errorMessage
                                newReleasesShowToast = true
                            }
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> {
                                NewComicListSec(state.newReleasesList.result,
                                    navController)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                        when(state.continueReadingList){
                            is DataState.Error -> LaunchedEffect(Unit){
                                continueReadingErrorMessage = state.continueReadingList.errorMessage
                                continueReadingLShowToast = true
                            }
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> {
                                CurrentReadComicListSec(state.continueReadingList.result,
                                    navController)
                            }
                        }
                    }
                    else -> CircularProgressIndicator()
                }
            }

        }
    }

}