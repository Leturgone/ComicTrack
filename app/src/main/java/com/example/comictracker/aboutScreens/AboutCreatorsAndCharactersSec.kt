package com.example.comictracker.aboutScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.data.CharacterItem
import com.example.comictracker.data.CreatorsMember
import com.example.comictracker.searchScreen.CharacterCard

@Composable
fun AboutCreatorsAndCharactersSec(navController: NavHostController){
    val creators = listOf(
        CreatorsMember("David Hine","writer",
            "http://i.annihil.us/u/prod/marvel/i/mg/9/10/4bad769d90b00.jpg"),
        CreatorsMember("Dennis Calero","colorist",
            "http://i.annihil.us/u/prod/marvel/i/mg/d/20/4bc5d5fe9786a.jpg"),

    )
    val characters = listOf(
        CharacterItem("Spider-Man (Peter Parker)",
            "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg")
    )
    Column(Modifier.fillMaxWidth()) {

        Text(text = "Creators",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        LazyRow{
            items(creators.size){
                val creator  = creators[it]
                Card(modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)) {
                        AsyncImage(
                            model = creator.image,
                            contentDescription = "creator",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Text(text = creator.name,
                            fontSize = 12.sp,)
                        Text(text = creator.category,
                            fontSize = 10.sp,)
                    }
                }
            }
        }


        Text(text = "Characters",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        LazyRow{
            items(characters.size){
                val character  = characters[it]
                CharacterCard(imageUrl = character.image, characterName = character.name , cardSize = 120, imageSize =50) {
                    navController.navigate("character")
                }
            }
        }
    }
}