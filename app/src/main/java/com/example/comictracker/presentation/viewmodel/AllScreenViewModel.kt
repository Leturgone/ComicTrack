package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AllScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllScreenViewModel @Inject constructor(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val remoteComicsRepository: RemoteComicsRepository,
    private val remoteCharacterRepository: RemoteCharacterRepository,
    private val localReadRepository: LocalReadRepository
): ViewModel(){

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:AllScreenIntent){
        when(intent){
            is AllScreenIntent.LoadAllCharactersScreen -> loadAllCharactersScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllCharacterSeriesScreen -> loadAllCharacterSeriesScreen(intent.characterId,intent.loadedCount)
            is AllScreenIntent.LoadAllCurrentReadingSeriesScreen -> loadAllCurrentReadingSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllDiscoverSeriesScreen -> loadAllDiscoverSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllLastComicScreen -> loadAllLastComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllLibComicScreen -> loadAllLibComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllLibSeriesScreen -> loadAllLibSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllMayLikeSeriesScreen -> loadAllMayLikeSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllNewComicScreen -> loadAllNewComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllNextComicScreen -> loadAllNextComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadReadListScreen -> loadReadListScreen(intent.loadedCount)
        }
    }

    private fun loadAllCharacterSeriesScreen(characterId: Int,loadedCount:Int) = viewModelScope.launch{
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val seriesDef = async {
            remoteSeriesRepository.getCharacterSeries(characterId,loadedCount).fold(
                onSuccess = {DataState.Success(it)},
                onFailure = {DataState.Error("Error loading comics with this character")}
            )
        }
        val series = seriesDef.await()
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }

    private fun loadAllCurrentReadingSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val currentSeriesFromBD = localReadRepository.loadCurrentReadIds(loadedCount)
        val currentSeries = remoteSeriesRepository.fetchSeries(currentSeriesFromBD).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all current reading")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(currentSeries)
    }

    private fun loadAllDiscoverSeriesScreen(loadedCount:Int) = viewModelScope.launch{
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val seriesDef = async {
            remoteSeriesRepository.getAllSeries(loadedCount).fold(
                onSuccess = {DataState.Success(it)},
                onFailure = {DataState.Error("Error loading comics with this character")}
            )
        }
        val series = seriesDef.await()
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }


    private fun loadAllCharactersScreen(loadedCount: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllCharactersScreenSate(DataState.Loading)
        val characterListDef  = async {
            try{
                DataState.Success(remoteCharacterRepository.getAllCharacters(loadedCount))
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading characters")
            }
        }
        val characters = characterListDef.await()
        _state.value = ComicAppState.AllCharactersScreenSate(characters)
    }

    private fun loadAllLastComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val result = try {
            val lastComicsFromBD = localReadRepository.loadHistory(loadedCount)
            val lastComics = remoteComicsRepository.fetchComics(lastComicsFromBD)
            DataState.Success(lastComics)
        }catch (e:Exception){
            Log.e("ViewModel","$e")
            DataState.Error("Error while loading last comics")
        }
        _state.value = ComicAppState.AllComicScreenSate(result)

    }

    private fun loadAllLibComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val result = try {
            val allComicsFromBD = localReadRepository.loadAllReadComicIds(loadedCount)
            val allComics = remoteComicsRepository.fetchComics(allComicsFromBD)
            DataState.Success(allComics)
        }catch (e:Exception){
            Log.e("ViewModel","$e")
            DataState.Error("Error while loading all comics from lib")
        }
        _state.value = ComicAppState.AllComicScreenSate(result)
    }

    private fun loadAllLibSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val allSeriesFromBD = localReadRepository.loadAllReadSeriesIds(loadedCount)
        val allSeriesResult = remoteSeriesRepository.fetchSeries(allSeriesFromBD).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all series from lib")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(allSeriesResult)
    }

    private fun loadAllMayLikeSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val loadedIdsSeriesFromBD = localReadRepository.loadAllReadSeriesIds(loadedCount)
        val mayLikeSeriesResult = remoteSeriesRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD).fold(
            onSuccess = { ids ->
                remoteSeriesRepository.fetchSeries(ids).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all may like series")}
                )
            },
            onFailure = {DataState.Error("Error while loading all may like series")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(mayLikeSeriesResult)
    }

    private fun loadAllNewComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val result = try {
            val loadedIdsSeriesFromBD = localReadRepository.loadCurrentReadIds(loadedCount)
            val newComics = remoteComicsRepository.fetchUpdatesForSeries(loadedIdsSeriesFromBD)
            DataState.Success(newComics)
        }catch (e:Exception){
            Log.e("ViewModel","$e")
            DataState.Error("Error while loading all new comics")
        }
        _state.value = ComicAppState.AllComicScreenSate(result)
    }

    private fun loadAllNextComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val result = try {
            val loadedIdsNextReadComicFromBD = localReadRepository.loadNextReadComicIds(loadedCount)
            val nextComics = remoteComicsRepository.fetchComics(loadedIdsNextReadComicFromBD)
            DataState.Success(nextComics)
        }catch (e:Exception){
            Log.e("ViewModel","$e")
            DataState.Error("Error while loading all next comics")
        }
        _state.value = ComicAppState.AllComicScreenSate(result)
    }

    private fun loadReadListScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val readlistFromBD = localReadRepository.loadWillBeReadIds(loadedCount)
        val readlistResult = remoteSeriesRepository.fetchSeries(readlistFromBD).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all readlist")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(readlistResult)
    }
}