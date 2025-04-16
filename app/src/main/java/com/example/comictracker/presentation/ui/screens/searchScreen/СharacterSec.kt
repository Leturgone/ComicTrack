package com.example.comictracker.presentation.ui.screens.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.domain.model.CharacterModel

/**
 * Character sec
 *
 * @param characterList
 * @param navController
 */
@Composable
fun CharacterSec(characterList: List<CharacterModel>, navController: NavHostController){
    Column {
        Text(text = "Characters",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.TopEnd){
            Text(text = "See all",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        navController.navigate("all_characters/0")
                    }
                    .padding(end = 15.dp, bottom = 12.dp))
        }
        Column(modifier = Modifier.padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                CharacterCard(imageUrl = characterList[0].image,
                    characterName = characterList[0].name,190,100){
                    navController.navigate("character/${characterList[0].characterId}"){
                        navController.navigate("character/${characterList[0].characterId}")
                    }
                }
                CharacterCard(imageUrl = characterList[1].image,
                    characterName = characterList[1].name,190,100){
                    navController.navigate("character/${characterList[1].characterId}"){
                        navController.navigate("character/${characterList[1].characterId}")
                    }
                }
            }
            Row {
                CharacterCard(imageUrl = characterList[2].image,
                    characterName = characterList[2].name,190,100){
                    navController.navigate("character/${characterList[2].characterId}"){
                        navController.navigate("character/${characterList[2].characterId}")
                    }
                }
                CharacterCard(imageUrl = characterList[3].image,
                    characterName = characterList[3].name,190,100){
                    navController.navigate("character/${characterList[3].characterId}"){
                        navController.navigate("character/${characterList[3].characterId}")
                    }
                }
            }

        }

    }

}

/**
 * Character card
 *
 * @param imageUrl
 * @param characterName
 * @param cardSize
 * @param imageSize
 * @param clickFun
 * @receiver
 */
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
