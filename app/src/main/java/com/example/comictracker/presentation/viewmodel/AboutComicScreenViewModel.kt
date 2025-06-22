package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.aboutComicUseCases.AboutComicUseCases
import com.example.comictracker.presentation.mvi.AboutComicScreenData
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AboutComicScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutComicScreenViewModel @Inject constructor(
    private val aboutComicUseCases: AboutComicUseCases
): ViewModel(){

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:AboutComicScreenIntent){
        when(intent){
            is AboutComicScreenIntent.LoadComicScreen -> loadComicScreen(intent.comicId)
            is AboutComicScreenIntent.MarkAsReadComic -> markAsReadComic(intent.comicId,intent.seriesId, intent.issueNumber)
            is AboutComicScreenIntent.MarkAsUnreadComic -> markAsUnreadComic(intent.comicId,intent.seriesId,intent.issueNumber)
        }
    }

    private fun loadComicScreen(comicId: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AboutComicScreenState(DataState.Loading)

        val comicDeferred = async {
            aboutComicUseCases.loadComicDataUseCase(comicId)
        }

        val characterListDeferred = async {
            aboutComicUseCases.loadComicCharactersUseCase(comicId)
        }

        val characterList = characterListDeferred.await().fold(
            onSuccess = {it},
            onFailure = {emptyList()}
        )

        val comicResult = comicDeferred.await().fold(
            onSuccess = {it},
            onFailure = {null}
        )
        if (comicResult!=null){
            val creatorList = aboutComicUseCases.loadComicCreatorsUseCase(comicResult).fold(
                onSuccess = {it},
                onFailure = { emptyList() }
            )
            _state.value = ComicAppState.AboutComicScreenState(DataState.Success(
                AboutComicScreenData(
                    comic = comicResult, creatorList = creatorList, characterList = characterList)
            ))
        }
        else{
            _state.value = ComicAppState.AboutComicScreenState(DataState.Error("Error loading this comic"))
        }
    }

    private fun markAsReadComic(comicApiId: Int, seriesApiId: Int,number:String)  = viewModelScope.launch{
        aboutComicUseCases.markComicAsReadUseCase(comicApiId, seriesApiId, number).onSuccess {
            loadComicScreen(comicApiId)
        }
    }
    private fun markAsUnreadComic(comicApiId: Int, seriesApiId: Int,number:String) = viewModelScope.launch {
        aboutComicUseCases.markComicAsUnreadUseCase(comicApiId, seriesApiId, number).onSuccess {
            loadComicScreen(comicApiId)
        }
    }
}