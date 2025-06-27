package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.aboutAllUseCases.AboutAllUseCases
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AllScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllScreenViewModel @Inject constructor(
    private val aboutAllUseCases:AboutAllUseCases
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
        val series = aboutAllUseCases.loadAllCharacterSeriesUseCase(characterId, loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading comics with this character")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }


    private fun loadAllCurrentReadingSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllCurrentReadingSeriesUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all current reading")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }


    private fun loadAllDiscoverSeriesScreen(loadedCount:Int) = viewModelScope.launch{
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val series = aboutAllUseCases.loadAllDiscoverSeriesUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading series")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }


    private fun loadAllCharactersScreen(loadedCount: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllCharactersScreenSate(DataState.Loading)
        val characters = aboutAllUseCases.loadAllCharactersUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading characters")}
        )
        _state.value = ComicAppState.AllCharactersScreenSate(characters)
    }


    private fun loadAllLastComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllLastComicUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading last comics")}
        )

        _state.value = ComicAppState.AllComicScreenSate(state)

    }


    private fun loadAllLibComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllLibComicUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all comics from lib")}
        )
        _state.value = ComicAppState.AllComicScreenSate(state)
    }


    private fun loadAllLibSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllLibSeriesUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all series from lib")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }


    private fun loadAllMayLikeSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllMayLikeSeriesUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all may like series")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }


    private fun loadAllNewComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllNewComicUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all new comics")}
        )
        _state.value = ComicAppState.AllComicScreenSate(state)
    }


    private fun loadAllNextComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllNextComicUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all next comics")}
        )
        _state.value = ComicAppState.AllComicScreenSate(state)
    }


    private fun loadReadListScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val state = aboutAllUseCases.loadAllReadListUseCase(loadedCount).fold(
            onSuccess = {DataState.Success(it)},
            onFailure = {DataState.Error("Error while loading all readlist")}
        )
        _state.value = ComicAppState.AllSeriesScreenSate(state)
    }
}