package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.homeUseCases.HomeUseCases
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.HomeScreenData
import com.example.comictracker.presentation.mvi.intents.HomeScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
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

        val newComicsDef = async { homeUseCases.loadNewComicsUseCase() }
        val nextComicsDef = async { homeUseCases.loadCurrentNextComicUseCase()}

        val newComics = newComicsDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = { DataState.Error("Error while loading new comics") }
        )
        val nextComics = nextComicsDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = { DataState.Error("Error while loading next comics") }
        )

        _state.value = ComicAppState.HomeScreenState(newComics,nextComics)
    }
}