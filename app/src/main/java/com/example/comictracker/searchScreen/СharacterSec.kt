package com.example.comictracker.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Preview
@Composable
fun CharacterSec(){
    Column {
        Text(text = "Characters",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
            Text(text = "See all",
                color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 15.dp, bottom = 12.dp))
        }
        Column(modifier = Modifier.padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg", characterName = "Character 1")
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg", characterName = "Character 2")
            }
            Row {
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg", characterName = "Character 3")
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg", characterName = "Character 4")
            }

        }

    }

}

@Composable
fun CharacterCard(imageUrl: String, characterName: String) {
    Card(modifier = Modifier.padding(8.dp).size(190.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().padding(8.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "character",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(text = characterName)
        }
    }
}