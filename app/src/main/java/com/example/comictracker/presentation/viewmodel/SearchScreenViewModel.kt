package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.LocalComicRepository
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
    private val localComicRepository: LocalComicRepository,
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
        val discoverSeriesListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteSeriesRepository.getAllSeries())
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading Discover Series")
            }
        }
        val mayLikeSeriesListDef  = async(Dispatchers.IO) {
            try{
                val loadedIdsSeriesFromBD = localComicRepository.loadAllReadSeriesIds(0)
                val mayLikeSeries= remoteSeriesRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD)
                DataState.Success(remoteSeriesRepository.fetchSeries(mayLikeSeries))
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading May Like Series")
            }
        }
        val characterListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteCharacterRepository.getAllCharacters())
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading characters")
            }
        }

        val dseries = discoverSeriesListDef.await()
        val mlsreis = mayLikeSeriesListDef.await()
        val characters = characterListDef.await()

        _state.value = ComicAppState.SearchScreenState(
            mlsreis,dseries,characters
        )
    }

    private fun loadSearchResults(query: String) = viewModelScope.launch{
        _state.value = ComicAppState.SearchResultScreenSate(DataState.Loading)
        val searchSeriesListDeferred = async(Dispatchers.IO) {
            try {
                DataState.Success(remoteSeriesRepository.getSeriesByTitle(query))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading results series")
            }

        }

        val searchCharacterListDeferred = async(Dispatchers.IO) {
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