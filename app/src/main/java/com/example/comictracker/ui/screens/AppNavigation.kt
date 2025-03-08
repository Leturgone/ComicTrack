package com.example.comictracker.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable("search_result/{search}") {
            val search = it.arguments!!.getString("search")
            SearchResultScreen(search!!,navController) }
        composable("all_characters/{loadCount}"){
            val loadCount = it.arguments!!.getString("loadCount")
            AllCharactersScreen(loadCount!!.toInt(),navController) }

        composable("comic/{comicId}") {
            val comicId = it.arguments!!.getString("comicId")
            ComicScreen(comicId!!.toInt(),navController) }

        composable("series/{seriesId}") {
            val seriesId = it.arguments?.getString("seriesId")
            SeriesScreen(seriesId!!.toInt(),navController)  }

        composable("character/{characterId}") {
            val characterId = it.arguments!!.getString("characterId")
            CharacterScreen(characterId!!.toInt(),navController) }

        composable("comics_from_series/{seriesId}"){
            val seriesId = it.arguments!!.getString("seriesId")
            AllComicSeriesSec(seriesId!!.toInt(), navController = navController) }

        composable("all_cs/{sourceId}/{category}/{loadCount}") {
            val sourceId = it.arguments!!.getString("sourceId")
            val category = it.arguments!!.getString("category")
            val loadCount = it.arguments!!.getString("loadCount")
            AllComicScreen(sourceId!!.toInt(), category!!,loadCount!!.toInt(), navController) }
    }
}