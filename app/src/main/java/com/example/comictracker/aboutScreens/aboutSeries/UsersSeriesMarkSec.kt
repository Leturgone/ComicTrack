package com.example.comictracker.aboutScreens.aboutSeries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comictracker.data.MarkCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UsersSeriesMarkSec() {
    val markCategories = listOf(
        MarkCategory(Icons.Filled.AccessTime,"Will be read"),
        MarkCategory(Icons.Filled.BookmarkAdded,"Read"),
        MarkCategory(Icons.Filled.Bookmark,"Currently reading"),
        MarkCategory(Icons.Filled.BookmarkRemove,"Unread"),

    )
    var mark  by remember { mutableStateOf("Unread") }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = Color.Gray
        )) {
        Box(
            Modifier
                .padding(10.dp)
                .clickable {
                    showBottomSheet = true
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
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(Modifier.fillMaxWidth(),Arrangement.SpaceBetween,Alignment.CenterHorizontally) {
                Text(
                    text = "Choose Category",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(200.dp)
                )
                LazyRow(Modifier.padding(10.dp), userScrollEnabled = false){
                    items(markCategories.size){
                        val markCategory = markCategories[it]
                        Column(Modifier.size(width = 100.dp, height = 180.dp).clickable {
                            mark = markCategory.title
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }, horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = markCategory.icon,
                                contentDescription ="${markCategory.title} icon",
                                modifier = Modifier.size(50.dp),
                                tint = MaterialTheme.colorScheme.primary)
                            Text(
                                text = markCategory.title,
                                fontSize = 20.sp,
                                modifier = Modifier.size(100.dp).padding(top = 10.dp),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }

        }
    }
}
