package com.example.comictracker.ui.screens.homeScreen

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
import com.example.comictracker.ui.screens.SeriesComicListCard
import com.example.comictracker.data.ComicCover



@Composable
fun NewComicListSec(navController: NavHostController){
    var newComicList: List<ComicCover> = listOf()

    Column {
        Text(text = "New releases",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
            Text(text = "See all",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 15.dp, bottom = 12.dp).clickable {
                    navController.navigate("all_cs")
                })
        }
        LazyRow{
            items(8){
                //val newComicCover  = newComicList[it]
                var lastPaddingEnd = 0
//                if (it == newComicList.size - 1){
//                    lastPaddingEnd = 16.dp
//                }
                SeriesComicListCard(title = "comic title",
                    image = "http://i.annihil.us/u/prod/marvel/i/mg/a/70/673f17fc798fa.jpg",
                    lastPaddingEnd =lastPaddingEnd,date = "Date" ) {
                    navController.navigate("comic")
                }

            }
        }
    }

}