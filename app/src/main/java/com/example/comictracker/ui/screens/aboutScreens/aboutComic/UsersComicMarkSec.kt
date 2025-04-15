package com.example.comictracker.ui.screens.aboutScreens.aboutComic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comictracker.mvi.ComicAppIntent
import com.example.comictracker.viewmodel.ComicViewModel


@Composable
fun UsersComicMarkSec(comicId: Int,
                      mark:String,
                      seriesId:Int,
                      number:String,
                      viewModel: ComicViewModel = hiltViewModel()) {

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
        Box(Modifier.padding(10.dp).clickable {
            when(mark){
                "unread" -> viewModel.processIntent(ComicAppIntent.MarkAsReadComic(comicId,seriesId,number))
                "read" -> viewModel.processIntent(ComicAppIntent.MarkAsUnreadComic(comicId,seriesId,number))
            }
        }) {
            Text(
                text = when(mark){
                    "unread" -> "Mark read"
                    "read" -> "Mark unread"
                    else -> {"Error"}
                },
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
            )
            Icon(imageVector = Icons.Filled.NavigateNext,
                contentDescription = "NextIcon",
                Modifier.padding(start = 310.dp))
        }

    }
}
