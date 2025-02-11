package com.example.comictracker.libaryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.comictracker.data.ComicCover

@Composable
fun LatestReadingSec(){
    var latestReadingComicList: List<ComicCover> = listOf()

    Column {
        Text(text = "Last updates",
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
                //val latestComicCover  = latestReadingComicList[it]
                var lastPaddingEnd = 0.dp
//                if (it == latestReadingComicList.size - 1){
//                    lastPaddingEnd = 16.dp
//                }
                Column(modifier = Modifier
                    .padding(start = 16.dp, end = lastPaddingEnd)) {
                    Card(modifier = Modifier
                        .width(127.dp)
                        .height(200.dp)) {
                        AsyncImage(model = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/59dfdd3078b52.jpg"
                            , contentDescription = "  current cover",modifier = Modifier
                                .width(145.dp)
                                .height(200.dp))
                    }
                    Text(text = "Date")
                }

            }
        }
    }
}