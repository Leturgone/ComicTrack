package com.example.comictracker.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.presentation.mvi.ComicAppIntent
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.viewmodel.ComicViewModel
import com.example.comictracker.R

@Composable
fun AllComicScreen(sourceId:Int,sourceCategory:String, loadCount:Int,
                   navController: NavHostController,
                   viewModel: ComicViewModel = hiltViewModel()){


    val uiState by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = sourceId) {
        viewModel.processIntent(ComicAppIntent.LoadAllScreen(sourceId,sourceCategory,loadCount))
    }

    Box {
        Column {
            Text(text = stringResource(id = R.string.all),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
            uiState.let {state ->
                when(state){
                    is ComicAppState.AllComicScreenSate ->{
                        when(state.comics){
                            is DataState.Error -> CustomToastMessage(
                                message = state.comics.errorMessage,
                                isVisible = showToast,
                                onDismiss = { showToast = false })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> {
                                ComicGridSec(comicList = state.comics.result,
                                    navController = navController)
                            }
                        }

                    }
                    is ComicAppState.AllSeriesScreenSate ->{
                        when(state.series){
                            is DataState.Error -> CustomToastMessage(
                                message = state.series.errorMessage,
                                isVisible = showToast,
                                onDismiss = { showToast = true })
                            DataState.Loading -> CircularProgressIndicator()
                            is DataState.Success -> {
                                SeriesGridSec(seriesList = state.series.result,
                                    navController = navController)
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
                navController.navigate("all_cs/$sourceId/$sourceCategory/${loadCount + 9}")
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
                    navController.navigate("all_cs/$sourceId/$sourceCategory/${loadCount - 9}")
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

@Composable
fun SeriesGridSec(seriesList:List<SeriesModel>,navController: NavHostController){
    LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize() ){
        items(seriesList.size){
            val series = seriesList[it]
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .clickable {
                    navController.popBackStack()
                    navController.navigate("series/${series.seriesId}")

                }
                ) {
                Card(modifier = Modifier.aspectRatio(0.7f)) {
                    AsyncImage(model = series.image
                        , contentDescription = "${series.title}  current cover",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun ComicGridSec(comicList:List<ComicModel>, navController: NavHostController ){
    LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize() ){
        items(comicList.size){
            val comic = comicList[it]
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .clickable {
                    navController.popBackStack()
                    navController.navigate("comic/${comic.comicId}")

                }
            ) {
                Card(modifier = Modifier.aspectRatio(0.7f)) {
                    AsyncImage(model = comic.image
                        , contentDescription = "${comic.title}  current cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth())
                }
            }
        }
    }
}