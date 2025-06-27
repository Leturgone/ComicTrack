package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.searchUseCases.SearchUseCases
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.SearchScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCases: SearchUseCases
): ViewModel() {
    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())

    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:SearchScreenIntent){
        when(intent){
            SearchScreenIntent.LoadSearchScreen -> loadSearchScreen()
            is SearchScreenIntent.Search -> loadSearchResults(intent.query)
        }
    }

    private fun loadSearchScreen() = viewModelScope.launch {
        _state.value = ComicAppState.SearchScreenState(DataState.Loading)

        val discoverSeriesListDef  = async { searchUseCases.loadNewSeriesListUseCase() }
        val mayLikeSeriesListDef  = async { searchUseCases.loadMayLikeSeriesListUseCase() }
        val characterListDef  = async { searchUseCases.loadCharactersListUseCase() }

        val discoverSeries = discoverSeriesListDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading Discover Series")}
        )
        val mayLikeSeries = mayLikeSeriesListDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading May Like Series")}
        )
        val characters = characterListDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading characters")}
        )

        _state.value = ComicAppState.SearchScreenState(
            mayLikeSeries,discoverSeries,characters
        )
    }

    private fun loadSearchResults(query: String) = viewModelScope.launch{
        _state.value = ComicAppState.SearchResultScreenSate(DataState.Loading)
        val searchSeriesListDeferred = async {
            searchUseCases.loadSeriesSearchResultListUseCase(query)
        }

        val searchCharacterListDeferred = async {
            searchUseCases.loadCharactersSearchResultListUseCase(query)
        }

        val seriesList = searchSeriesListDeferred.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading results series")}
        )
        val characterList = searchCharacterListDeferred.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading results characters")}
        )

        _state.value = ComicAppState.SearchResultScreenSate(characterList,seriesList)
    }
}