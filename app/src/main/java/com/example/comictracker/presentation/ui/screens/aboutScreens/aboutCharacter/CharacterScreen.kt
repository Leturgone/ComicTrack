package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutCharacter

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
import com.example.comictracker.presentation.mvi.intents.AboutCharacterScreenIntent
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.viewmodel.AboutCharacterScreenViewModel


@Composable
fun CharacterScreen(
    characterId: Int,
    navController: NavHostController,
    viewModel: AboutCharacterScreenViewModel = hiltViewModel()
    ){
    val uiState by viewModel.state.collectAsState()
    var showCharacterToast by remember { mutableStateOf(false) }
    var showSeriesToast by remember { mutableStateOf(false) }
    var characterErrorMessage by remember { mutableStateOf("") }
    var seriesErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = characterId) {
        viewModel.processIntent(AboutCharacterScreenIntent.LoadCharacterScreen(characterId))
    }

    Box {
        Column() {
            uiState.let {state ->
                when(state){
                    is ComicAppState.AboutCharacterScreenState ->{
                        when(state.character){
                            is DataState.Error -> LaunchedEffect(Unit){
                                characterErrorMessage = state.character.errorMessage
                                showCharacterToast = true
                            }
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> CharacterSec(state.character.result)
                        }

                        when(state.series){
                            is DataState.Error -> LaunchedEffect(Unit){
                                seriesErrorMessage= state.series.errorMessage
                                showSeriesToast = true
                            }
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> CharacterSeriesSec(
                                characterId,
                                state.series.result,
                                navController)
                        }
                    }
                    else -> {CircularProgressIndicator()}
                }
            }

        }
        Column {
            CustomToastMessage(
                message = characterErrorMessage,
                isVisible = showCharacterToast,
                onDismiss = { showCharacterToast = false })
            Spacer(modifier = Modifier.height(12.dp))
            CustomToastMessage(
                message = seriesErrorMessage,
                isVisible = showSeriesToast,
                onDismiss = {showSeriesToast = false })
        }

    }

}