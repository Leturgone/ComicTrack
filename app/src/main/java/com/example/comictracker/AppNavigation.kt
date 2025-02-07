package com.example.comictracker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("home"){}
        composable("search"){}
        composable("libary"){}
    }
}