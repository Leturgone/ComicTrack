package com.example.comictracker.mvi

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.model.StatisticsforAll

sealed class ComicAppState {
    data class HomeScreenState(
        val newReleasesList:DataState<List<ComicModel>> = DataState.Loading,
        val continueReadingList:DataState<List<ComicModel>> = DataState.Loading
    ): ComicAppState()
    data class SearchScreenState(
        val mayLikeList:DataState<List<SeriesModel>> = DataState.Loading,
        val discoverList:DataState<List<SeriesModel>> = DataState.Loading,
        val charactersSec:DataState<List<CharacterModel>> = DataState.Loading
    ): ComicAppState()

    data class MyLibraryScreenState(
        val statistics:DataState<StatisticsforAll> = DataState.Loading,
        val favoritesList: DataState<List<SeriesModel>> = DataState.Loading,
        val currentlyReadingList: DataState<List<SeriesModel>>  = DataState.Loading,
        val lastUpdates:DataState<List<ComicModel>> = DataState.Loading
    ): ComicAppState()

    data class AboutComicScreenState(
        val comic: DataState<ComicModel> = DataState.Loading,
        val creatorList: DataState<List<CreatorModel>> = DataState.Loading,
        val characterList: DataState<List<CharacterModel>> = DataState.Loading
    ): ComicAppState()

    data class AboutSeriesScreenState(
        val series: DataState<SeriesModel> = DataState.Loading,
        val comicList: DataState<List<ComicModel>> = DataState.Loading,
        val creatorList: DataState<List<CreatorModel>> = DataState.Loading,
        val characterList: DataState<List<CharacterModel>> = DataState.Loading,
        val connectedSeriesList: DataState<List<SeriesModel>> = DataState.Loading
    ): ComicAppState()

    data class AboutCharacterScreenState(
        val character: DataState<CharacterModel> = DataState.Loading,
        val series: DataState<List<SeriesModel>> = DataState.Loading
    ): ComicAppState()

    data class AllSeriesScreenSate(
        val series: DataState<List<SeriesModel>> = DataState.Loading,
    ): ComicAppState()

    data class AllComicScreenSate(
        val series: DataState<List<ComicModel>> = DataState.Loading,
    ): ComicAppState()

    data class SearchResultScreenSate(
        val character: DataState<List<CharacterModel>> = DataState.Loading,
        val series: DataState<List<SeriesModel>> = DataState.Loading
    ): ComicAppState()
}