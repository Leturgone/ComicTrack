package com.example.comictracker.presentation.ui.screens.aboutScreens.aboutSeries

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.R
import com.example.comictracker.presentation.mvi.intents.AboutSeriesScreenIntent
import com.example.comictracker.presentation.viewmodel.AboutSeriesScreenViewModel

@Composable
fun NextComicSec(nextComic:ComicModel?,navController: NavHostController,
                 viewModel:AboutSeriesScreenViewModel = hiltViewModel()
                 ){
    if (nextComic != null) {
        Column {
            Text(
                text = stringResource(id = R.string.continue_reading),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Text(text = stringResource(id = R.string.see_all),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(end = 15.dp, bottom = 12.dp)
                        .clickable {
                            navController.navigate("comics_from_series/${nextComic.seriesId}/0")
                        })
            }
            ComicFromSeriesCard(nextComic, navController){
                when (nextComic.readMark) {
                    "read" ->
                        viewModel.processIntent(
                            AboutSeriesScreenIntent.MarkAsUnreadLastComic(
                                nextComic.comicId,
                                nextComic.seriesId,
                                nextComic.number
                            )
                        )

                    else -> viewModel.processIntent(
                        AboutSeriesScreenIntent.MarkAsReadNextComic(
                            nextComic.comicId,
                            nextComic.seriesId,
                            nextComic.number,
                        )
                    )
                }
            }
        }
    }

}