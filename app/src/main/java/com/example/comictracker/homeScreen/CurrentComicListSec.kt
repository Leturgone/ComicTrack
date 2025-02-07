package com.example.comictracker.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview()
@Composable
 fun CurrentReadComicListSec(){

     Column {
      Text(text = "Continue reading",
       fontSize = 24.sp,
       color = MaterialTheme.colorScheme.onBackground,
       fontWeight = FontWeight.Bold,
       modifier = Modifier.padding(16.dp))
     }
 }