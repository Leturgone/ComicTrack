package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AboutCharacterScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutCharacterScreenViewModel @Inject constructor(
    private val characterRepository: RemoteCharacterRepository,
    private val seriesRepository: RemoteSeriesRepository,
): ViewModel() {

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())

    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:AboutCharacterScreenIntent){
        when(intent){
            is AboutCharacterScreenIntent.LoadCharacterScreen -> loadCharacterScreen(intent.characterId)
        }
    }

    private fun loadCharacterScreen(characterId:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AboutCharacterScreenState(
            character = DataState.Loading,
            series = DataState.Loading
        )
        val characterDef = async(Dispatchers.IO) {
            try {
                DataState.Success(characterRepository.getCharacterById(characterId))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading character")
            }

        }

        val seriesDef = async(Dispatchers.IO) {
            try{
                DataState.Success(seriesRepository.getCharacterSeries(characterId))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading comics with this character")
            }
        }

        val character = characterDef.await()

        _state.value = ComicAppState.AboutCharacterScreenState(
            character =  character,
            series = DataState.Loading
        )

        _state.value = ComicAppState.AboutCharacterScreenState(
            character =  character,
            series = seriesDef.await()
        )
    }

}