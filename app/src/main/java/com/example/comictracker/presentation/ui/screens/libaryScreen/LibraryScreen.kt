package com.example.comictracker.presentation.ui.screens.libaryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
    var showToast by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = "libaryscreen") {
        viewModel.processIntent(LibraryScreenIntent.LoadLibraryScreen)
    }
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
                        when(state.dataState){
                            is DataState.Error -> CustomToastMessage(
                                message = state.dataState.errorMessage,
                                isVisible = showToast,
                                onDismiss = { showToast = false })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> {
                                MicroSectionsSec(state.dataState.result.statistics,navController)
                                FavoriteSec(state.dataState.result.favoritesList,navController)
                                CurrentReadingSec(state.dataState.result.currentlyReadingList,navController)
                                LatestReadingSec(state.dataState.result.lastUpdates,navController)
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

}