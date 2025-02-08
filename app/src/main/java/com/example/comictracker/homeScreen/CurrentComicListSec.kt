package com.example.comictracker.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comictracker.data.ComicCover


lateinit var currentComicList: List<ComicCover>
@Preview()
@Composable
 fun CurrentReadComicListSec(){

     Column {
         Text(text = "Continue reading",
             fontSize = 24.sp,
             color = MaterialTheme.colorScheme.onBackground,
             fontWeight = FontWeight.Bold,
             modifier = Modifier.padding(16.dp))

         LazyRow{
             items(currentComicList.size){
                 val currentComicCover  = currentComicList[it]
                 var lastPaddingEnd = 0.dp
                 if (it == currentComicList.size - 1){
                     lastPaddingEnd = 16.dp
                 }
                 Box(modifier = Modifier
                     .padding(start = 16.dp, end = lastPaddingEnd)) {
                     //Image( contentDescription =" ${currentComicCover.title} current cover" )
                 }
             }
         }
     }
 }