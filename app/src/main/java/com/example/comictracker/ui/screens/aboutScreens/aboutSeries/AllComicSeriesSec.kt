package com.example.comictracker.ui.screens.aboutScreens.aboutSeries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comictracker.data.ComicCover

@Composable
fun AllComicSeriesSec(navController: NavHostController){
    val comics = listOf(
        ComicCover("Spider-Man Noir #1","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null),
        ComicCover("Spider-Man Noir #2","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null),
        ComicCover("Spider-Man Noir #3","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null),
        ComicCover("Spider-Man Noir #4","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null),
        ComicCover("Spider-Man Noir #5","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null),
        ComicCover("Spider-Man Noir #6","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null),
        ComicCover("Spider-Man Noir #7","http://i.annihil.us/u/prod/marvel/i/mg/5/e0/5bc77a942112a.jpg",null)
    )
    Column {
        AboutSeriesSec()
        Text(text = "All Comics ",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        LazyColumn{
            items(comics.size){
                ComicFromSeriesCard(navController)
            }
        }
    }
}