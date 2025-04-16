package com.example.comictracker.presentation.mvi

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.model.StatisticsforAll

/**
 * Comic app state
 *
 * @constructor Create empty Comic app state
 */
sealed class ComicAppState {
    /**
     * Home screen state
     *
     * @property dataState
     * @constructor Create empty Home screen state
     */
    data class HomeScreenState(
        val dataState: DataState<HomeScreenData> = DataState.Loading
    ): ComicAppState()

    /**
     * Search screen state
     *
     * @property mayLikeList
     * @property discoverList
     * @property charactersSec
     * @constructor Create empty Search screen state
     */
    data class SearchScreenState(
        val mayLikeList: DataState<List<SeriesModel>> = DataState.Loading,
        val discoverList: DataState<List<SeriesModel>> = DataState.Loading,
        val charactersSec: DataState<List<CharacterModel>> = DataState.Loading
    ): ComicAppState()

    /**
     * My library screen state
     *
     * @property dataState
     * @constructor Create empty My library screen state
     */
    data class MyLibraryScreenState(
        val dataState: DataState<MyLibraryScreenData> = DataState.Loading
    ): ComicAppState()

    /**
     * About comic screen state
     *
     * @property dataState
     * @constructor Create empty About comic screen state
     */
    data class AboutComicScreenState(
        val dataState: DataState<AboutComicScreenData> = DataState.Loading
    ): ComicAppState()

    /**
     * About series screen state
     *
     * @property dataState
     * @constructor Create empty About series screen state
     */
    data class AboutSeriesScreenState(
        val dataState: DataState<AboutSeriesScreenData> = DataState.Loading
    ): ComicAppState()

    /**
     * All comic series screen state
     *
     * @property comicFromSeriesList
     * @constructor Create empty All comic series screen state
     */
    data class AllComicSeriesScreenState(
        val comicFromSeriesList: DataState<List<ComicModel>> = DataState.Loading,
    ): ComicAppState()

    /**
     * About character screen state
     *
     * @property character
     * @property series
     * @constructor Create empty About character screen state
     */
    data class AboutCharacterScreenState(
        val character: DataState<CharacterModel> = DataState.Loading,
        val series: DataState<List<SeriesModel>> = DataState.Loading
    ): ComicAppState()

    /**
     * All series screen sate
     *
     * @property series
     * @constructor Create empty All series screen sate
     */
    data class AllSeriesScreenSate(
        val series: DataState<List<SeriesModel>> = DataState.Loading,
    ): ComicAppState()

    /**
     * All comic screen sate
     *
     * @property comics
     * @constructor Create empty All comic screen sate
     */
    data class AllComicScreenSate(
        val comics: DataState<List<ComicModel>> = DataState.Loading,
    ): ComicAppState()

    /**
     * Search result screen sate
     *
     * @property character
     * @property series
     * @constructor Create empty Search result screen sate
     */
    data class SearchResultScreenSate(
        val character: DataState<List<CharacterModel>> = DataState.Loading,
        val series: DataState<List<SeriesModel>> = DataState.Loading
    ): ComicAppState()

    /**
     * All characters screen sate
     *
     * @property character
     * @constructor Create empty All characters screen sate
     */
    data class AllCharactersScreenSate(
        val character: DataState<List<CharacterModel>> = DataState.Loading
    ) : ComicAppState()
}


/**
 * Home screen data
 *
 * @property newReleasesList
 * @property continueReadingList
 * @constructor Create empty Home screen data
 */
data class HomeScreenData(
    val newReleasesList: List<ComicModel> = emptyList(),
    val continueReadingList: List<ComicModel> = emptyList()
)


/**
 * My library screen data
 *
 * @property statistics
 * @property favoritesList
 * @property currentlyReadingList
 * @property lastUpdates
 * @constructor Create empty My library screen data
 */
data class MyLibraryScreenData(
    val statistics: StatisticsforAll = StatisticsforAll(0,0,0,0,0),
    val favoritesList: List<SeriesModel> = emptyList(),
    val currentlyReadingList: List<SeriesModel>  = emptyList(),
    val lastUpdates: List<ComicModel> = emptyList()
): ComicAppState()

/**
 * About comic screen data
 *
 * @property comic
 * @property creatorList
 * @property characterList
 * @constructor Create empty About comic screen data
 */
data class AboutComicScreenData(
    val comic: ComicModel? = null,
    val creatorList: List<CreatorModel> = emptyList(),
    val characterList: List<CharacterModel> = emptyList()
)

/**
 * About series screen data
 *
 * @property series
 * @property comicList
 * @property creatorList
 * @property characterList
 * @property connectedSeriesList
 * @property nextRead
 * @constructor Create empty About series screen data
 */
data class AboutSeriesScreenData(
    val series: SeriesModel? = null,
    val comicList: List<ComicModel> = emptyList(),
    val creatorList: List<CreatorModel> = emptyList(),
    val characterList: List<CharacterModel> = emptyList(),
    val connectedSeriesList: List<SeriesModel> = emptyList(),
    val nextRead: ComicModel? = null
)