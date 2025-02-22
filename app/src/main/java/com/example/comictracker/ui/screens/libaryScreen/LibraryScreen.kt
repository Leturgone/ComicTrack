package com.example.comictracker.ui.screens.libaryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LibraryScreen(navController: NavHostController){
    Column {
        Text(text = "My Library",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))
        Column(Modifier.verticalScroll(rememberScrollState())) {
            MicroSectionsSec(navController)
            FavoriteSec(navController)
            CurrentReadingSec(navController)
            LatestReadingSec(navController)
            Spacer(modifier = Modifier.padding(bottom = 40.dp))
        }

    }

}