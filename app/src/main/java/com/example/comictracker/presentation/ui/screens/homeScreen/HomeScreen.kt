package com.example.comictracker.presentation.ui.screens.homeScreen

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
import com.example.comictracker.presentation.mvi.ComicAppIntent
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.viewmodel.ComicViewModel

@Composable
fun HomeScreen(navController: NavHostController,viewModel: ComicViewModel = hiltViewModel()){
    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = "homeScreen") {
        viewModel.processIntent(ComicAppIntent.LoadHomeScreen)
    }
    Column {
        uiState.let {state->
            when(state){
                is ComicAppState.HomeScreenState ->{
                    when(state.dataState){
                        is DataState.Error -> CustomToastMessage(
                            message = state.dataState.errorMessage,
                            isVisible = showToast,
                            onDismiss = { showToast = false })
                        DataState.Loading -> CircularProgressIndicator()
                        is DataState.Success -> {
                            NewComicListSec(state.dataState.result.newReleasesList,
                                navController)
                            Spacer(modifier = Modifier.height(12.dp))
                            CurrentReadComicListSec(state.dataState.result.continueReadingList,
                                navController)
                        }
                    }
                }
                else -> CircularProgressIndicator()
            }
        }

    }
}