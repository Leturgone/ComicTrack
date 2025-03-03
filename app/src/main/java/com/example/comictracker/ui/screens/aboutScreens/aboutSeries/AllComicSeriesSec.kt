package com.example.comictracker.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.data.model.ComicCover
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.mvi.ComicAppIntent
import com.example.comictracker.mvi.ComicAppState
import com.example.comictracker.mvi.DataState
import com.example.comictracker.ui.screens.CustomToastMessage
import com.example.comictracker.viewmodel.ComicViewModel

@Composable
fun AllComicSeriesSec(seriesId:Int,
                      viewModel: ComicViewModel = hiltViewModel(),
                      navController: NavHostController){

    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = seriesId) {
        viewModel.processIntent(ComicAppIntent.LoadComicFromSeriesScreen(seriesId))
    }


    Column {
        Text(text = "All Comics ",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        uiState.let { state ->
            when(state){
                is ComicAppState.AllComicSeriesScreenState -> {
                    when(state.comicFromSeriesList){
                        is DataState.Error -> CustomToastMessage(
                            message = state.comicFromSeriesList.errorMessage,
                            isVisible = showToast,
                            onDismiss = { showToast = false })
                        DataState.Loading -> CircularProgressIndicator()
                        is DataState.Success -> {
                            val comics = state.comicFromSeriesList.result
                            LazyColumn{
                                items(comics.size){
                                    val comic = comics[it]
                                    ComicFromSeriesCard(comic,navController)
                                }
                            }
                        }
                    }
                }

                else -> {
                    CircularProgressIndicator()
                }
            }
        }

    }
}