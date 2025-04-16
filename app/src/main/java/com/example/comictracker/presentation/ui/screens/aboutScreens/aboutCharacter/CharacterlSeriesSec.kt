package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutCharacter

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.domain.model.SeriesModel

/**
 * Character series sec
 *
 * @param characterId
 * @param seriesList
 * @param navController
 */
@Composable
fun CharacterSeriesSec(characterId:Int,seriesList: List<SeriesModel>, navController: NavHostController){
    Box {
        Column {
            Text(text = "All",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
            Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
                Text(text = "See all",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navController.navigate("all_cs/$characterId/characterSeries/0")
                    }.padding(end = 15.dp, bottom = 12.dp))
            }
            LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize() ){
                items(seriesList.size){
                    val series = seriesList[it]
                    Column(modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {

                            navController.popBackStack()
                            navController.navigate("series/${series.seriesId}")

                        }) {
                        Card(modifier = Modifier.aspectRatio(0.7f)) {
                            AsyncImage(model = series.image
                                , contentDescription = "${series.title}  current cover",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}