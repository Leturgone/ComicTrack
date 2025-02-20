package com.example.comictracker.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.data.CharacterItem

@Composable
fun CharacterSec(navController: NavHostController){
    Column {
        Text(text = "Characters",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
            Text(text = "See all",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate("all_characters")
                }.padding(end = 15.dp, bottom = 12.dp))
        }
        Column(modifier = Modifier.padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg",
                    characterName = "Character 1",190,100){
                    navController.navigate("character")
                }
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg",
                    characterName = "Character 2",190,100){
                    navController.navigate("character")
                }
            }
            Row {
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg",
                    characterName = "Character 3",190,100){
                    navController.navigate("character")
                }
                CharacterCard(imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/90/54edf8eb8f102.jpg",
                    characterName = "Character 4",190,100){
                    navController.navigate("character")
                }
            }

        }

    }

}

@Composable
fun CharacterCard(imageUrl: String, characterName: String,cardSize :Int,imageSize:Int, clickFun:() -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .size(cardSize.dp)
        .clickable(onClick = clickFun)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "character",
                modifier = Modifier
                    .size(imageSize.dp)
                    .clip(CircleShape)
            )
            Text(text = characterName,fontSize = 12.sp,
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun  AllCharactersScreen(navController: NavHostController){
    val characters = listOf(
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg")
    )

    Column {
        Text(text = "All",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize() ){
            items(characters.size){
                val character = characters[it]
                CharacterCard(imageUrl = character.image,
                    characterName =character.name , cardSize =190 , imageSize =100 ) {
                    navController.navigate("character")
                }
            }
        }
    }
}