package com.example.comictracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun SeriesComicListCard(title: String,
    image: String, lastPaddingEnd:Int, date:String? = null, clickFun:() -> Unit){
    Column(modifier = Modifier
        .padding(start = 16.dp, end = lastPaddingEnd.dp).clickable(onClick = clickFun)) {
        Card(modifier = Modifier
            .width(127.dp)
            .height(200.dp)) {
            AsyncImage(model = image,
                    contentScale = ContentScale.FillBounds,
                contentDescription = "${title}  current cover",modifier = Modifier
                    .width(145.dp)
                    .height(200.dp))
        }
        date?.let { Text(text = it)}
    }
}