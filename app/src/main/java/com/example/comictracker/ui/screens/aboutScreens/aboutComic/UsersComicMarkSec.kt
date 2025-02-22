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

@Preview
@Composable
fun UsersComicMarkSec(){
    var mark by remember {
        mutableStateOf("Mark read")
    }
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
                "Mark read" -> mark = "Mark unread"
                "Mark unread" -> mark = "Mark read"
            }
        }) {
            Text(
                text = mark,
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
