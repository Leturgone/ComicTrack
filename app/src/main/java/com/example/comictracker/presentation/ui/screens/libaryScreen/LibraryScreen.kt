package com.example.comictracker.presentation.ui.screens.libaryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.R
import com.example.comictracker.presentation.mvi.intents.LibraryScreenIntent
import com.example.comictracker.presentation.viewmodel.LibraryScreenViewModel

@Composable
fun LibraryScreen(navController: NavHostController,
                  viewModel: LibraryScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.state.collectAsState()
    var statShowToast by remember { mutableStateOf(false) }
    var statErrorMessage by remember { mutableStateOf("") }
    var favoriteShowToast by remember { mutableStateOf(false) }
    var favoriteErrorMessage by remember { mutableStateOf("") }
    var currentlyReadingShowToast by remember { mutableStateOf(false) }
    var currentlyReadingErrorMessage by remember { mutableStateOf("") }
    var lastUpadtesShowToast by remember { mutableStateOf(false) }
    var lastUpadtesErrorMessage by remember { mutableStateOf("") }


    LaunchedEffect(key1 = "libaryscreen") {
        viewModel.processIntent(LibraryScreenIntent.LoadLibraryScreen)
    }
    Box{
        Column {
            Text(text = stringResource(id = R.string.my_library),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
            Column(Modifier.verticalScroll(rememberScrollState())) {
                uiState.let {state ->
                    when(state){
                        is ComicAppState.MyLibraryScreenState ->{
                            when(state.statistics){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    statErrorMessage = state.statistics.errorMessage
                                    statShowToast = true
                                }
                                DataState.Loading -> CircularProgressIndicator()
                                is DataState.Success -> {
                                    MicroSectionsSec(state.statistics.result,navController)
                                }
                            }
                            when(state.favoritesList){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    favoriteErrorMessage = state.favoritesList.errorMessage
                                    favoriteShowToast = true
                                }
                                DataState.Loading -> CircularProgressIndicator()
                                is DataState.Success -> {
                                    FavoriteSec(state.favoritesList.result,navController)
                                }
                            }
                            when(state.currentlyReadingList){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    currentlyReadingErrorMessage = state.currentlyReadingList.errorMessage
                                    currentlyReadingShowToast = true
                                }
                                DataState.Loading -> CircularProgressIndicator()
                                is DataState.Success -> {
                                    CurrentReadingSec(state.currentlyReadingList.result,navController)
                                }
                            }
                            when(state.lastUpdates){
                                is DataState.Error -> LaunchedEffect(Unit){
                                    lastUpadtesErrorMessage = state.lastUpdates.errorMessage
                                    lastUpadtesShowToast = true
                                }
                                DataState.Loading -> CircularProgressIndicator()
                                is DataState.Success -> {
                                    LatestReadingSec(state.lastUpdates.result,navController)
                                    Spacer(modifier = Modifier.padding(bottom = 40.dp))
                                }
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
                message = statErrorMessage,
                isVisible = statShowToast,
                onDismiss = { statShowToast= false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = favoriteErrorMessage ,
                isVisible = favoriteShowToast ,
                onDismiss = { favoriteShowToast  = false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = currentlyReadingErrorMessage,
                isVisible = currentlyReadingShowToast,
                onDismiss = { currentlyReadingShowToast = false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = lastUpadtesErrorMessage,
                isVisible = lastUpadtesShowToast,
                onDismiss = { lastUpadtesShowToast = false })
        }

    }
}