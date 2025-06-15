package com.example.comictracker.presentation.viewmodel

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
            remoteSeriesRepository.getCharacterSeries(characterId,loadedCount)
        }
        val series = seriesDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error loading comics with this character")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }


    private fun loadAllCurrentReadingSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = localReadRepository.loadCurrentReadIds(loadedCount).fold(
            onSuccess = {currentSeriesFromBD ->
                remoteSeriesRepository.fetchSeries(currentSeriesFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all current reading")}
                )
            },
            onFailure = {DataState.Error("Error while loading all current reading")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }


    private fun loadAllDiscoverSeriesScreen(loadedCount:Int) = viewModelScope.launch{
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val seriesDef = async {
            remoteSeriesRepository.getAllSeries(loadedCount)
        }
        val series = seriesDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error loading comics with this character")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }


    private fun loadAllCharactersScreen(loadedCount: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllCharactersScreenSate(DataState.Loading)
        val characterListDef  = async {
            remoteCharacterRepository.getAllCharacters(loadedCount)
        }
        val characters = characterListDef.await().fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error loading characters")}
        )
        _state.value = ComicAppState.AllCharactersScreenSate(characters)
    }


    private fun loadAllLastComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = localReadRepository.loadHistory(loadedCount).fold(
            onSuccess = {lastComicsFromBD ->
                remoteComicsRepository.fetchComics(lastComicsFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading last comics")}
                )
            },
            onFailure = {DataState.Error("Error while loading last comics")}
        )

        _state.value = ComicAppState.AllComicScreenSate(state)

    }

    private fun loadAllLibComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = localReadRepository.loadAllReadComicIds(loadedCount).fold(
            onSuccess = {allComicsFromBD ->
                remoteComicsRepository.fetchComics(allComicsFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all comics from lib")}
                )
            },
            onFailure = {DataState.Error("Error while loading all comics from lib")}
        )
        _state.value = ComicAppState.AllComicScreenSate(state)
    }

    private fun loadAllLibSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = localReadRepository.loadAllReadSeriesIds(loadedCount).fold(
            onSuccess = {allSeriesFromBD ->
                remoteSeriesRepository.fetchSeries(allSeriesFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all series from lib")}
                )
            },
            onFailure = {DataState.Error("Error while loading all series from lib")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }

    private fun loadAllMayLikeSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = localReadRepository.loadAllReadSeriesIds(loadedCount).fold(
            onSuccess = {loadedIdsSeriesFromBD ->
                remoteSeriesRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD).fold(
                    onSuccess = { ids ->
                        remoteSeriesRepository.fetchSeries(ids).fold(
                            onSuccess = {DataState.Success(it)},
                            onFailure = {DataState.Error("Error while loading all may like series")}
                        )
                    },
                    onFailure = {DataState.Error("Error while loading all may like series")}
                )
            },
            onFailure = {DataState.Error("Error while loading all may like series")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }

    private fun loadAllNewComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = localReadRepository.loadCurrentReadIds(loadedCount).fold(
            onSuccess = {loadedIdsSeriesFromBD ->
                remoteComicsRepository.fetchUpdatesForSeries(loadedIdsSeriesFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all new comics")}
                )
            },
            onFailure = {
                DataState.Error("Error while loading all new comics")
            }
        )
        _state.value = ComicAppState.AllComicScreenSate(state)
    }

    private fun loadAllNextComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = localReadRepository.loadNextReadComicIds(loadedCount).fold(
            onSuccess = { loadedIdsNextReadComicFromBD ->
                remoteComicsRepository.fetchComics(loadedIdsNextReadComicFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all next comics")}
                )
            },
            onFailure = {
                DataState.Error("Error while loading all next comics")
            }
        )
        _state.value = ComicAppState.AllComicScreenSate(state)
    }

    private fun loadReadListScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = localReadRepository.loadWillBeReadIds(loadedCount).fold(
            onSuccess = {readlistFromBD->
                remoteSeriesRepository.fetchSeries(readlistFromBD).fold(
                    onSuccess = {DataState.Success(it)},
                    onFailure = {DataState.Error("Error while loading all readlist")}
                )
            },
            onFailure = {DataState.Error("Error while loading all readlist")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }
}