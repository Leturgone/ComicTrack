package com.example.comictracker.presentation.ui.screens.libaryScreen

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
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.presentation.ui.screens.SeriesComicListCard

/**
 * Latest reading sec
 *
 * @param latestReadList
 * @param navController
 */
@Composable
fun LatestReadingSec(latestReadList: List<ComicModel>, navController: NavHostController){

    Column {
        Text(text = "Last updates",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
            Text(text = "See all",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate("all_cs/0/lastComic/0")
                }.padding(end = 15.dp, bottom = 12.dp))
        }
        LazyRow{
            items(latestReadList.size){
                val latestComic  = latestReadList[it]
                var lastPaddingEnd = 0
                if (it == latestReadList.size - 1){
                    lastPaddingEnd = 16
                }
                SeriesComicListCard(title = latestComic.title,
                    image = latestComic.image,
                    lastPaddingEnd =lastPaddingEnd, date = latestComic.date ) {
                    navController.navigate("comic/${latestComic.comicId}")

                }

            }
        }
    }
}