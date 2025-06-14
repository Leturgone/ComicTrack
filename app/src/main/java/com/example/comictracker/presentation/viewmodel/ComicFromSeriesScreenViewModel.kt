package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.ComicFromSeriesScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ComicFromSeriesScreenViewModel @Inject constructor(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localWriteRepository: LocalWriteRepository,
    private val localReadRepository: LocalReadRepository
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
        withContext(Dispatchers.IO){
            _state.emit(remoteComicsRepository.getComicsFromSeries(seriesId,loadedCount).fold(
                onSuccess = {
                    val comicsWithReadMarks = it.map { comic ->
                        async {
                            val readMark = localReadRepository.loadComicMark(comic.comicId)
                            Log.i("ReadMarK", "${comic.number} $readMark")
                            comic.copy(readMark = readMark)
                        }
                    }.awaitAll()
                    ComicAppState.AllComicSeriesScreenState(DataState.Success(comicsWithReadMarks))
                },
                onFailure = { ComicAppState.AllComicSeriesScreenState(
                    DataState.Error("Error loading comic from this series"))}
            )
            )
        }
    }

    private fun markAsReadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int) = viewModelScope.launch{
        val nextComicId = async {
            remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await().fold(
            onSuccess = {it},
            onFailure = {null}
        )
        if (localWriteRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }

    }

    private fun markAsUnreadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int)  = viewModelScope.launch{
        val prevComicId = async {
            remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await().fold(
            onSuccess = {it},
            onFailure = {null}
        )

        if (localWriteRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }
    }
}