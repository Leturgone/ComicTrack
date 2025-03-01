package com.example.comictracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.mvi.ComicAppIntent
import com.example.comictracker.mvi.ComicAppState
import com.example.comictracker.mvi.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ComicViewModel @Inject constructor(
    private val remoteComicRepository: RemoteComicRepository):ViewModel(){

    // Начальное состояние
    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())

    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent: ComicAppIntent){
        when(intent){
            is ComicAppIntent.LoadCharacterScreen -> loadCharacterScreen(intent.character)
            is ComicAppIntent.LoadComicScreen -> TODO()
            ComicAppIntent.LoadHomeScreen -> TODO()
            ComicAppIntent.LoadProfileScreen -> TODO()
            ComicAppIntent.LoadSearchScreen -> TODO()
            is ComicAppIntent.LoadSeriesScreen -> TODO()
            is ComicAppIntent.MarkAsCurrentlyReadingSeries -> TODO()
            is ComicAppIntent.MarkAsReadComic -> TODO()
            is ComicAppIntent.MarkAsReadSeries -> TODO()
            is ComicAppIntent.MarkAsUnreadComic -> TODO()
            is ComicAppIntent.MarkAsUnreadSeries -> TODO()
            is ComicAppIntent.MarkAsWillBeReadSeries -> TODO()
            is ComicAppIntent.Search -> TODO()
        }

    }

    private fun loadCharacterScreen(character:CharacterModel) = viewModelScope.launch {
        _state.value = ComicAppState.AboutCharacterScreenState(
            character = DataState.Success(character), series = DataState.Loading
        )
        try {
            withContext(Dispatchers.IO){
                _state.emit(ComicAppState.AboutCharacterScreenState(
                    character = DataState.Success(character),
                    series = DataState.Success(remoteComicRepository.getCharacterSeries(character.characterId))
                ))

            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                _state.value = ComicAppState.AboutCharacterScreenState(
                    character = DataState.Success(character),
                    series = DataState.Error("Error loading comics with this character")
                )
            }
        }


    }
}