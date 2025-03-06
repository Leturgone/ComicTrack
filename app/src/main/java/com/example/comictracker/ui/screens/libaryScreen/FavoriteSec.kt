package com.example.comictracker.ui.screens.libaryScreen

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
import com.example.comictracker.domain.model.SeriesModel

@Composable
fun FavoriteSec(favoriteList: List<SeriesModel>, navController: NavHostController){

    Column {
        Text(text = "Favorites",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        LazyRow{
            items(favoriteList.size){
                val favoriteSeries  = favoriteList[it]
                var lastPaddingEnd = 0
                if (it == favoriteList.size - 1){
                    lastPaddingEnd = 16
                }
                SeriesComicListCard(title = favoriteSeries.title!!,
                    image = favoriteSeries.image!!,
                    lastPaddingEnd =lastPaddingEnd ) {
                    navController.navigate("series/${favoriteSeries.seriesId}")

                }

            }
        }
    }
}