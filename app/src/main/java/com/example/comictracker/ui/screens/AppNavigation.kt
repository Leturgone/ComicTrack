package com.example.comictracker.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.comictracker.ui.screens.AllComicScreen
import com.example.comictracker.ui.screens.aboutScreens.aboutCharacter.CharacterScreen
import com.example.comictracker.ui.screens.aboutScreens.aboutComic.ComicScreen
import com.example.comictracker.ui.screens.aboutScreens.aboutSeries.AllComicSeriesSec
import com.example.comictracker.ui.screens.aboutScreens.aboutSeries.SeriesScreen
import com.example.comictracker.ui.screens.homeScreen.HomeScreen
import com.example.comictracker.ui.screens.libaryScreen.LibraryScreen
import com.example.comictracker.ui.screens.searchScreen.AllCharactersScreen
import com.example.comictracker.ui.screens.searchScreen.SearchResultScreen
import com.example.comictracker.ui.screens.searchScreen.SearchScreen


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("home"){ HomeScreen(navController) }
        composable("search"){ SearchScreen(navController) }
        composable("library"){ LibraryScreen(navController) }
        composable("search_result") { SearchResultScreen(navController) }
        composable("all_characters"){ AllCharactersScreen(navController) }

        composable("comic") { ComicScreen(navController) }
        composable("series") { SeriesScreen(navController)  }
        composable("character") { CharacterScreen(navController) }
        composable("comics_from_series"){ AllComicSeriesSec(navController) }
        composable("all_cs") { AllComicScreen(navController) }
    }
}