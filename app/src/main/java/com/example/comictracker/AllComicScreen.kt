package com.example.comictracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.data.ComicCover

@Composable
fun AllComicScreen(navController: NavHostController){
    val comics = listOf(
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","13.02.2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","13.02.2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","13.02.2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","13.02.2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","13.02.2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","13.02.2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg",null)
    )
    Column {
        Text(text = "All",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize() ){
            items(comics.size){
                val comic = comics[it]
                Column(modifier = Modifier
                    .padding(start = 16.dp).clickable {

                        navController.popBackStack()
                        navController.navigate("series")

                    }) {
                    Card(modifier = Modifier.aspectRatio(0.7f)) {
                        AsyncImage(model = comic.imageUrl
                            , contentDescription = "${comic.title}  current cover",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth())
                    }
                    comic.date?.let { data -> Text(text = data) }
                }
            }
        }
    }

}