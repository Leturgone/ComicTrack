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
        val dataState: DataState<AboutComicScreenData> = DataState.Loading
    ): ComicAppState()

    data class AboutSeriesScreenState(
        val dataState: DataState<AboutSeriesScreenData> = DataState.Loading
    ): ComicAppState()

    data class AllComicSeriesScreenState(
        val comicFromSeriesList:DataState<List<ComicModel>> = DataState.Loading,
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




data class HomeScreenData(
    val newReleasesList: List<ComicModel> = emptyList(),
    val continueReadingList: List<ComicModel> = emptyList()
)
data class SearchScreenData(
    val mayLikeList: List<SeriesModel> = emptyList(),
    val discoverList: List<SeriesModel> = emptyList(),
    val charactersSec: List<CharacterModel> = emptyList()
): ComicAppState()

data class MyLibraryScreenData(
    val statistics: StatisticsforAll = StatisticsforAll(0,0,0),
    val favoritesList: List<SeriesModel> = emptyList(),
    val currentlyReadingList: List<SeriesModel>  = emptyList(),
    val lastUpdates: List<ComicModel> = emptyList()
): ComicAppState()

data class AboutComicScreenData(
    val comic: ComicModel? = null,
    val creatorList: List<CreatorModel> = emptyList(),
    val characterList: List<CharacterModel> = emptyList()
)

data class AboutSeriesScreenData(
    val series: SeriesModel? = null,
    val comicList: List<ComicModel> = emptyList(),
    val creatorList: List<CreatorModel> = emptyList(),
    val characterList: List<CharacterModel> = emptyList(),
    val connectedSeriesList: List<SeriesModel> = emptyList()
)

data class AboutCharacterScreenData(
    val character: CharacterModel? = null,
    val series: List<SeriesModel> = emptyList()
)


data class SearchResultScreenData(
    val character: List<CharacterModel> = emptyList(),
    val series: List<SeriesModel> = emptyList()
)