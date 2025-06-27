package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.comicFromSeriesUseCases.ComicFromSeriesUseCases
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.ComicFromSeriesScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicFromSeriesScreenViewModel @Inject constructor(
    private val comicFromSeriesUseCases: ComicFromSeriesUseCases
): ViewModel(){

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:ComicFromSeriesScreenIntent){
        when(intent){
            is ComicFromSeriesScreenIntent.LoadComicFromSeriesScreen -> loadComicFromSeriesScreen(intent.seriesId,intent.loadCount)
            is ComicFromSeriesScreenIntent.MarkAsReadComicInList -> markAsReadComicInList(intent.comicId,intent.seriesId, intent.issueNumber,intent.loadedCount)
            is ComicFromSeriesScreenIntent.MarkAsUnreadComicInList -> markAsUnreadComicInList(intent.comicId,intent.seriesId, intent.issueNumber,intent.loadedCount)
        }
    }


    private fun loadComicFromSeriesScreen(seriesId: Int,loadedCount: Int)  = viewModelScope.launch{
        _state.value = ComicAppState.AllComicSeriesScreenState(DataState.Loading)
        val result = comicFromSeriesUseCases.loadComicListFromSeriesWithMarksUseCase(seriesId,loadedCount).fold(
            onSuccess = { DataState.Success(it) },
            onFailure = { DataState.Error("Error while loading comic from this series")}
        )
        _state.value = ComicAppState.AllComicSeriesScreenState(result)
    }


    private fun markAsReadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int) = viewModelScope.launch{
        comicFromSeriesUseCases.markComicAsReadInComicListUseCase(comicApiId, seriesApiId, number).onSuccess {
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }
    }


    private fun markAsUnreadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int)  = viewModelScope.launch{
        comicFromSeriesUseCases.markComicAsUnreadInComicListUseCase(comicApiId, seriesApiId, number).onSuccess {
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }
    }
}