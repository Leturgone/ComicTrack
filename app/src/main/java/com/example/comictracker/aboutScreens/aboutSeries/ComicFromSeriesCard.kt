package com.example.comictracker.aboutScreens.aboutSeries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Preview
@Composable
fun ComicFromSeriesCard(){
    var checkedIconColor by remember { mutableStateOf(Color.Gray) }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = Color.Gray
        )
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            AsyncImage(model = "http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",
                contentDescription = "current cover",modifier = Modifier
                    .width(80.dp)
                    .height(100.dp))
            Column {
                Text(
                    text = "Number of comic ",
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                )
                Text(
                    text = "Date ",
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                )
            }
            Column {
                Icon(imageVector = Icons.Filled.Check,
                    contentDescription = "ReadIcon",
                    tint = checkedIconColor,
                    modifier = Modifier
                        .padding(start = 80.dp)
                        .clickable {
                            when(checkedIconColor){
                                Color.Gray -> checkedIconColor = Color.Green
                                Color.Green -> checkedIconColor = Color.Gray
                            }
                        })
            }

        }
    }
}