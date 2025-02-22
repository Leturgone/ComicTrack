package com.example.comictracker.ui.screens.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.data.ComicCover

@Composable
fun SearchResultScreen(navController: NavHostController){
    val searchRes = listOf(
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg","2025"),
        ComicCover("Spider -Man","http://i.annihil.us/u/prod/marvel/i/mg/c/e0/4bc4947ea8f4d.jpg",null)
    )
    
    Column {
        Text(text = "Search result",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        
        LazyColumn{
            items(searchRes.size){
                val comic = searchRes[it]
                ComicCard(comic){
                        navController.navigate("series")
                }

            }
        }
    }

}

@Composable
fun ComicCard(comic: ComicCover,clickFun:() -> Unit){
    Row(Modifier.clickable(onClick = clickFun)) {
        Column {
            Text(text = comic.title,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))

            Column(modifier = Modifier
                .padding(start = 16.dp)) {
                Card(modifier = Modifier
                    .width(127.dp)
                    .height(200.dp)) {
                    AsyncImage(model = comic.imageUrl
                        , contentDescription = "  current cover",modifier = Modifier
                            .width(145.dp)
                            .height(200.dp))
                }
            }

        }
        comic.date?.let {
            Text(text = it,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 200.dp, top = 56.dp))
        }
    }
}