package com.example.comictracker.presentation.ui.screens.homeScreen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.presentation.ui.screens.SeriesComicListCard
import com.example.comictracker.R

@Composable
 fun CurrentReadComicListSec(currentComicList: List<ComicModel>, navController: NavHostController){
     Column {
         Text(text = stringResource(id = R.string.continue_reading),
             fontSize = 24.sp,
             color = MaterialTheme.colorScheme.onBackground,
             fontWeight = FontWeight.Bold,
             modifier = Modifier.padding(16.dp))
         Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
             Text(text = stringResource(id = R.string.see_all),
                 color = MaterialTheme.colorScheme.primary,
                 modifier = Modifier
                     .padding(end = 15.dp, bottom = 12.dp)
                     .clickable {
                         navController.navigate("all_cs/0/nextComics/0")
                     })
         }
         LazyRow{
             items(currentComicList.size){
                 val currentComic  = currentComicList[it]
                 var lastPaddingEnd = 0
                if (it == currentComicList.size - 1){
                    lastPaddingEnd = 16
                }
                 SeriesComicListCard(title = currentComic.title,
                     image = currentComic.image,
                     lastPaddingEnd = lastPaddingEnd) {
                     navController.navigate("comic/${currentComic.comicId}")
                 }
             }
         }
     }
 }