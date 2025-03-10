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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.ui.screens.aboutScreens.ExpandableText


@Composable
fun CharacterSec(character: CharacterModel) {
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