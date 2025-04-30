package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.MyLibraryScreenData
import com.example.comictracker.presentation.mvi.intents.LibraryScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val remoteComicsRepository: RemoteComicsRepository,
    //private val localComicRepository: LocalComicRepository,
    private val localWriteRepository: LocalWriteRepository,
    private val localReadRepository: LocalReadRepository
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

        val loadedStatisticsFromBD = localReadRepository.loadStatistics()
        val loadedFavoriteSeriesIdsFromBD = localReadRepository.loadFavoritesIds()
        val loadedCurrentlyReadingSeriesIdsFromBD = localReadRepository.loadCurrentReadIds(0)
        val loadedHistoryReadComicFromBD = localReadRepository.loadHistory(0)

        val favoriteSeriesDef = loadedFavoriteSeriesIdsFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteSeriesRepository.getSeriesById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }
        val currentSeriesDef = loadedCurrentlyReadingSeriesIdsFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteSeriesRepository.getSeriesById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }

        val lastComicsDef = loadedHistoryReadComicFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteComicsRepository.getComicById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }

        val favoriteSeries = favoriteSeriesDef.awaitAll().filterNotNull()
        val currentSeries = currentSeriesDef.awaitAll().filterNotNull()
        val lastComics = lastComicsDef.awaitAll().filterNotNull()

        _state.value = ComicAppState.MyLibraryScreenState(
            DataState.Success(
                MyLibraryScreenData(
                    statistics = loadedStatisticsFromBD,
                    favoritesList = favoriteSeries,
                    currentlyReadingList = currentSeries,
                    lastUpdates = lastComics
                )
            ))
    }
}