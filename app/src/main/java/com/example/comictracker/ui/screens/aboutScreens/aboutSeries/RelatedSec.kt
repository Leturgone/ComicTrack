package com.example.comictracker.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comictracker.ui.screens.SeriesComicListCard
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel


@Composable
fun RelatedSec(connectedSeries:List<SeriesModel?>, navController: NavHostController){

    Column {
        Text(text = "Connected",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        when(connectedSeries.isEmpty()){
            true -> {
                Text(text = "No connected series",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(16.dp))
            }
            false -> {
                LazyRow{
                    items(connectedSeries.size){
                        val currentSeries  = connectedSeries[it]
                        var lastPaddingEnd = 0
                        if (it == connectedSeries.size - 1){
                            lastPaddingEnd = 16
                        }
                        currentSeries?.let {
                            SeriesComicListCard(title = currentSeries.title?:"null",
                                image = currentSeries.image?:"null",
                                lastPaddingEnd = lastPaddingEnd ) {
                                navController.navigate("series")
                            }
                        }

                    }
                }
            }
        }
    }
}