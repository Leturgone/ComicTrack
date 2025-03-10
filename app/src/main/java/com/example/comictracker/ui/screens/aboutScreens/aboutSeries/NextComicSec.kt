package com.example.comictracker.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comictracker.domain.model.ComicModel

@Composable
fun NextComicSec(comicList:List<ComicModel>,navController: NavHostController){
    if (comicList.isNotEmpty()) {
        Column {
            Text(
                text = "Continue reading",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Text(text = "See all",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 15.dp, bottom = 12.dp)
                        .clickable {
                            navController.navigate("comics_from_series/${comicList[0].seriesId}/0")
                        })
            }
            ComicFromSeriesCard(comicList[0], navController)
        }
    }

}