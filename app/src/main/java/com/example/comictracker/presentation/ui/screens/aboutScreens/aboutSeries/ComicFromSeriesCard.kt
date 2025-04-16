package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.presentation.mvi.ComicAppIntent
import com.example.comictracker.presentation.viewmodel.ComicViewModel

/**
 * Comic from series card
 *
 * @param comic
 * @param navController
 * @param loadCount
 * @param viewModel
 */
@Composable
fun ComicFromSeriesCard(comic:ComicModel,navController: NavHostController,
                        loadCount:Int?,
                        viewModel: ComicViewModel = hiltViewModel()){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("comic/${comic.comicId}") },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = Color.Gray
        )
    ) {
        Box() {
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.TopStart) {
                Row() {
                    AsyncImage(
                        model = comic.image,
                        contentDescription = "current cover", modifier = Modifier
                            .width(80.dp)
                            .height(100.dp)
                    )
                    Column {
                        Text(
                            text = "#${comic.number}",
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                        )
                        Text(
                            text = comic.date,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                        )
                    }

                }
            }
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.TopEnd){
                Column {
                    Icon(imageVector = Icons.Filled.Check,
                        contentDescription = "ReadIcon",
                        tint = when(comic.readMark){
                            "read" -> Color.Green
                            else -> Color.Gray
                        },
                        modifier = Modifier
                            .padding(start = 40.dp)
                            .clickable {
                                when (comic.readMark) {
                                     "read"->
                                         viewModel.processIntent(
                                        ComicAppIntent.MarkAsUnreadComicInList(comic.comicId,comic.seriesId,comic.number,loadCount!!))

                                    else -> viewModel.processIntent(
                                        ComicAppIntent.MarkAsReadComicInList(comic.comicId,comic.seriesId,comic.number,loadCount))
                                }
                            }
                    )
                }
            }
        }
    }
}