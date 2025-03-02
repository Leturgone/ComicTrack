package com.example.comictracker.ui.screens.libaryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.navigation.NavHostController
import com.example.comictracker.ui.screens.SeriesComicListCard
import com.example.comictracker.data.model.ComicCover
import com.example.comictracker.viewmodel.ComicViewModel

@Composable
fun FavoriteSec(navController: NavHostController,
                viewModel: ComicViewModel = hiltViewModel()){
    var favoritesComicList: List<Int> = listOf()

    Column {
        Text(text = "Favorites",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        LazyRow{
            items(8){
                val favoriteComicId  = favoritesComicList[it]
                var lastPaddingEnd = 0
                if (it == favoritesComicList.size - 1){
                    lastPaddingEnd = 16
                }
                SeriesComicListCard(title = "${favoritesComicList[it]} comic title",
                    image = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/59dfdd3078b52.jpg",
                    lastPaddingEnd =lastPaddingEnd ) {
                    navController.navigate("series")

                }

            }
        }
    }
}