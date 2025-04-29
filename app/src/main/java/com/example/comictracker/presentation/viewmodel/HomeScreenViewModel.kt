package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.LocalComicRepository
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.HomeScreenData
import com.example.comictracker.presentation.mvi.intents.HomeScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val remoteComicRepository: RemoteComicRepository,
    private val localComicRepository: LocalComicRepository,
): ViewModel() {
    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:HomeScreenIntent){
        when(intent){
            HomeScreenIntent.LoadHomeScreen -> loadHomeScreen()
        }
    }

    private fun loadHomeScreen() = viewModelScope.launch {
        _state.value = ComicAppState.HomeScreenState(DataState.Loading)

        val loadedIdsSeriesFromBD = localComicRepository.loadCurrentReadIds(0)
        val loadedIdsNextReadComicFromBD = localComicRepository.loadNextReadComicIds(0)

        val newComics = remoteComicRepository.fetchUpdatesForSeries(loadedIdsSeriesFromBD)
        val nextComics = remoteComicRepository.fetchComics(loadedIdsNextReadComicFromBD)

        _state.value = ComicAppState.HomeScreenState(
            DataState.Success(
                HomeScreenData(
                    newReleasesList = newComics, continueReadingList = nextComics
                )
            ))
    }
}