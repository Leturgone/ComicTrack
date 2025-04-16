package com.example.comictracker.presentation.ui.screens.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.presentation.ui.screens.SeriesComicListCard

/**
 * May like sec
 *
 * @param mayLikeComicList
 * @param navController
 */
@Composable
fun MayLikeSec(mayLikeComicList: List<SeriesModel>, navController: NavHostController){

    Column {
        Text(text = "May like",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
            Text(text = "See all",
                color = MaterialTheme.colorScheme.primary, modifier = Modifier
                    .padding(end = 15.dp, bottom = 12.dp)
                    .clickable { navController.navigate("all_cs/0/mayLike/0")  })
        }
        LazyRow{
            items(mayLikeComicList.size){
                val newSeries  = mayLikeComicList[it]
                var lastPaddingEnd = 0
                if (it == mayLikeComicList.size - 1){
                    lastPaddingEnd = 16
                }
                SeriesComicListCard(
                    title = newSeries.title!!,
                    image =newSeries.image!! ,
                    lastPaddingEnd = lastPaddingEnd) {
                    navController.navigate("series/${newSeries.seriesId}")
                }
            }
        }
    }
}