package com.example.comictracker.ui.screens.aboutScreens.aboutCharacter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.comictracker.ui.screens.aboutScreens.ExpandableText
import com.example.comictracker.data.model.CharacterItem

@Preview
@Composable
fun CharacterSec(){
    val character = CharacterItem("Spider-Man (Peter Parker)",
        "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.",
        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg")
    Row{
        Column{
            Text(
                text = character.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.width(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "DESCRIPTION",
                fontSize = 12.sp,
                color = Color.Gray
            )
            ExpandableText(text = character.desc, width = 200)

        }
        AsyncImage(
            model = character.image,
            contentDescription = "character",
            modifier = Modifier.padding(start = 10.dp)
                .size(150.dp)
                .clip(CircleShape)
        )
    }
}