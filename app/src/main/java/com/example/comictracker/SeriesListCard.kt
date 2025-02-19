package com.example.comictracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun SeriesListCard(image: String,lastPaddingEnd:Int,date:String? = null,clickFun:() -> Unit){
    Column(modifier = Modifier
        .padding(start = 16.dp, end = lastPaddingEnd.dp).clickable(onClick = clickFun)) {
        Card(modifier = Modifier
            .width(127.dp)
            .height(200.dp)) {
            AsyncImage(model = image
                , contentDescription = "  current cover",modifier = Modifier
                    .width(145.dp)
                    .height(200.dp))
        }
        date?.let { Text(text = it)}
    }
}