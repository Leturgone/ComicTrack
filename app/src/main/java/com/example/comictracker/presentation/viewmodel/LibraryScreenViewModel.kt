package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.libraryUseCases.LibraryUseCases
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.MyLibraryScreenData
import com.example.comictracker.presentation.mvi.intents.LibraryScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val libraryUseCases: LibraryUseCases
):ViewModel() {
    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())

    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:LibraryScreenIntent){
        when(intent){
            LibraryScreenIntent.LoadLibraryScreen -> loadLibraryScreen()
        }
    }

    private fun loadLibraryScreen() = viewModelScope.launch {
        _state.value = ComicAppState.MyLibraryScreenState(DataState.Loading)

        val loadedStatisticsFromBDDef = async {libraryUseCases.loadStatisticsUseCase()}
        val favoriteSeriesDef = async { libraryUseCases.loadFavoriteSeriesUseCase() }
        val currentSeriesDef = async {libraryUseCases.loadCurrentlyReadingSeriesUseCase() }
        val lastComicsDef = async {libraryUseCases.loadHistoryReadComicUseCase() }

        val favoriteSeries = favoriteSeriesDef.await().fold(
            onSuccess = {it},
            onFailure = { emptyList() }
        )
        val currentSeries = currentSeriesDef.await().fold(
            onSuccess = {it},
            onFailure = { emptyList() }
        )
        val lastComics = lastComicsDef.await().fold(
            onSuccess = {it},
            onFailure = { emptyList() }
        )
        val loadedStatisticsFromBD = loadedStatisticsFromBDDef.await().fold(
            onSuccess = {it},
            onFailure = {null}
        )

        val result = if(loadedStatisticsFromBD == null) {
            DataState.Error("Error while loading library screen")
        }else {
            DataState.Success(
                MyLibraryScreenData(
                    statistics = loadedStatisticsFromBD,
                    favoritesList = favoriteSeries,
                    currentlyReadingList = currentSeries,
                    lastUpdates = lastComics
                )
            )
        }

        _state.value = ComicAppState.MyLibraryScreenState(result)
    }
}