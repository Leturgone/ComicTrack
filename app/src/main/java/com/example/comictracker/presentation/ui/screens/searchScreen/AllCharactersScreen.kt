package com.example.comictracker.presentation.ui.screens.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.presentation.mvi.ComicAppIntent
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.ui.screens.CustomToastMessage
import com.example.comictracker.presentation.viewmodel.ComicViewModel

@Composable
fun  AllCharactersScreen(loadCount: Int, navController: NavHostController,
                         viewModel: ComicViewModel = hiltViewModel()){

    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = "allcharscreen") {
        viewModel.processIntent(ComicAppIntent.LoadAllCharactersScreen(loadCount))
    }

    Box {
        Column {
            Text(text = "All",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
            uiState.let {state ->
                when(state){
                    is ComicAppState.AllCharactersScreenSate ->{
                        when(state.character){
                            is DataState.Error -> CustomToastMessage(
                                message = state.character.errorMessage,
                                isVisible = showToast,
                                onDismiss = { showToast = false })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> {
                                LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp),
                                    modifier = Modifier.fillMaxSize() ){
                                    items(state.character.result.size){
                                        val character = state.character.result[it]
                                        CharacterCard(imageUrl = character.image,
                                            characterName =character.name , cardSize =190 , imageSize =100 ) {
                                            navController.navigate("character/${character.characterId}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else -> {
                        CircularProgressIndicator()}
                }
            }
        }
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxSize()) {
            IconButton(onClick = {
                navController.popBackStack()
                navController.navigate("all_characters/${loadCount + 9}")
            },
                modifier = Modifier.padding(16.dp),
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primary
                ),
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.NavigateNext,
                    contentDescription = "nextButton")
            }
        }
        if (loadCount>=9){
            Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()) {
                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate("all_characters/${loadCount - 9}")
                },
                    modifier = Modifier.padding(16.dp),
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "backButton")
                }
            }
        }

    }


}