package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.SearchScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val remoteCharacterRepository: RemoteCharacterRepository,
    private val localReadRepository: LocalReadRepository
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
        val discoverSeriesListDef  = async {
            remoteSeriesRepository.getAllSeries()
        }
        val mayLikeSeriesListDef  = async(Dispatchers.IO) {
            val loadedIdsSeriesFromBD = localReadRepository.loadAllReadSeriesIds(0)
            remoteSeriesRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD).fold(
                onSuccess = { ids ->
                    remoteSeriesRepository.fetchSeries(ids).fold(
                        onSuccess = {DataState.Success(it)},
                        onFailure = {DataState.Error("Error loading May Like Series")}
                    )
                },
                onFailure = {DataState.Error("Error loading May Like Series")}
            )
        }
        val characterListDef  = async {
            try{
                DataState.Success(remoteCharacterRepository.getAllCharacters())
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading characters")
            }
        }

        val dseries = discoverSeriesListDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error loading Discover Series")}
        )
        val mlsreis = mayLikeSeriesListDef.await()
        val characters = characterListDef.await()

        _state.value = ComicAppState.SearchScreenState(
            mlsreis,dseries,characters
        )
    }

    private fun loadSearchResults(query: String) = viewModelScope.launch{
        _state.value = ComicAppState.SearchResultScreenSate(DataState.Loading)
        val searchSeriesListDeferred = async {
            remoteSeriesRepository.getSeriesByTitle(query).fold(
                onSuccess = {DataState.Success(it)},
                onFailure = {DataState.Error("Error loading results series")}
            )
        }

        val searchCharacterListDeferred = async {
            try {
                DataState.Success(remoteCharacterRepository.getCharactersByName(query))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading results characters")
            }

        }

        val seriesList = searchSeriesListDeferred.await()
        val characterList = searchCharacterListDeferred.await()

        _state.value = ComicAppState.SearchResultScreenSate(characterList,seriesList)
    }
}