package com.example.comictracker.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.data.ComicCover


@Composable
 fun CurrentReadComicListSec(navController: NavHostController){
    var currentComicList: List<ComicCover> = listOf()
     Column {
         Text(text = "Continue reading",
             fontSize = 24.sp,
             color = MaterialTheme.colorScheme.onBackground,
             fontWeight = FontWeight.Bold,
             modifier = Modifier.padding(16.dp))
         Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
             Text(text = "See all",
                 color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 15.dp, bottom = 12.dp))
         }
         LazyRow{
             items(8){
                 //val currentComicCover  = currentComicList[it]
                 var lastPaddingEnd = 0.dp
//                if (it == currentComicList.size - 1){
//                    lastPaddingEnd = 16.dp
//                }
                 Column(modifier = Modifier
                     .padding(start = 16.dp, end = lastPaddingEnd)) {
                     Card(modifier = Modifier
                         .width(127.dp)
                         .height(200.dp)) {
                         Box(modifier = Modifier.fillMaxSize()){
                         AsyncImage(model = "http://i.annihil.us/u/prod/marvel/i/mg/d/50/679ba099d9ebe.jpg"
                             , contentDescription = "  current cover",modifier = Modifier
                                 .width(145.dp)
                                 .height(200.dp))
                         }

                     }
                 }
             }
         }
     }
 }