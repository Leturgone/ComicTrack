package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.presentation.ui.screens.aboutScreens.ExpandableText
import com.example.comictracker.R
@Composable
fun AboutSeriesSec(series: SeriesModel){
    Column {
        Row(Modifier.fillMaxWidth()){
            Column {
                Text(
                    text = series.title?: stringResource(id = R.string.no_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.width(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.date),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = series.date?: stringResource(id = R.string.no_date),
                    fontSize = 14.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.desc),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExpandableText(text = series.desc?: stringResource(id = R.string.no_desc))

            }

            Card(modifier = Modifier
                .padding(start = 60.dp)
                .width(127.dp)
                .height(200.dp)) {
                Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.TopEnd){
                    AsyncImage(model = series.image,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = " ${series.title} current cover",modifier = Modifier
                            .width(145.dp)
                            .height(200.dp))
                }

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}