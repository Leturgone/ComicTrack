package com.example.comictracker.aboutScreens.aboutComic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.comictracker.aboutScreens.ExpandableText

@Composable
fun AboutComicSec(navController: NavHostController){
    Column {
        Row(Modifier.fillMaxWidth()){
            Column {
                Text(
                    text = "Spider-Man Noir #1",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.width(200.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "SERIES",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Spider-Man Noir",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.width(200.dp).clickable { navController.navigate("series") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "WRITER",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Julius Onah",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "DATE",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Date",
                    fontSize = 14.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "DESCRIPTION",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExpandableText(text = "After meeting with newly elected U.S. President Thaddeus Ross, Sam finds himself in the middle of an international incident. He must discover the rea")

            }

            Card(modifier = Modifier
                .padding(start = 60.dp)
                .width(127.dp)
                .height(200.dp)) {
                Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.TopEnd){
                    AsyncImage(model = "http://i.annihil.us/u/prod/marvel/i/mg/b/f0/4bb5fbe177b30.jpg"
                        , contentDescription = "  current cover",modifier = Modifier
                            .width(145.dp)
                            .height(200.dp))
                }

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}