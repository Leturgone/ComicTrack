package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.LocalComicRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.ComicFromSeriesScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ComicFromSeriesScreenViewModel @Inject constructor(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localComicRepository: LocalComicRepository,
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
        try {
            withContext(Dispatchers.IO){
                _state.emit(
                    ComicAppState.AllComicSeriesScreenState(
                        DataState.Success(
                            remoteComicsRepository.getComicsFromSeries(seriesId,loadedCount).map {
                                val readMark = localComicRepository.loadComicMark(it.comicId)
                                it.copy(readMark = readMark)
                            })
                    )

                )
            }
        }catch (e:Exception){
            _state.value = ComicAppState.AllComicSeriesScreenState(
                DataState.Error("Error loading comic from this series : $e"))
        }

    }

    private fun markAsReadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int) = viewModelScope.launch{
        val nextComicId = async {
            remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await()

        if (localComicRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }
    }

    private fun markAsUnreadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int)  = viewModelScope.launch{
        val prevComicId = async {
            remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await()

        if (localComicRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }
    }
}