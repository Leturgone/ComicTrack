package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
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
    private val remoteComicsRepository: RemoteComicsRepository,
    private val remoteCharacterRepository: RemoteCharacterRepository,
    private val remoteCreatorsRepository: RemoteCreatorsRepository,
    private val localWriteRepository: LocalWriteRepository,
    private val localReadRepository: LocalReadRepository
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
            remoteComicsRepository.getComicById(comicId)
        }

        val characterListDeferred = async {
            remoteCharacterRepository.getComicCharacters(comicId)
        }

        val comic = comicDeferred.await().fold(
            onSuccess = {it},
            onFailure = {emptyList<ComicModel>()}
        )


        val creatorListDeferred = async {
            if (comic is ComicModel){
                remoteCreatorsRepository.getComicCreators(comic.creators).getOrDefault(emptyList())
            } else {
                emptyList()
            }
        }

        val characterList = characterListDeferred.await().fold(
            onSuccess = {it},
            onFailure = {emptyList()}
        )
        val creatorList = creatorListDeferred.await()

        _state.value = (ComicAppState.AboutComicScreenState(
            when(comic){
                is ComicModel ->{
                    localReadRepository.loadComicMark(comic.comicId).fold(
                        onSuccess = {
                            val comicWithMark = comic.copy(readMark = it)
                            DataState.Success(
                                AboutComicScreenData(
                                    comic = comicWithMark, creatorList = creatorList, characterList = characterList)
                            )
                        },
                        onFailure = {
                            DataState.Error("Error loading this series")
                        }
                    )
                }
                else ->  DataState.Error("Error loading this series")
            }))
    }

    private fun markAsReadComic(comicApiId: Int, seriesApiId: Int,number:String)  = viewModelScope.launch{
        async {
            remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await().onSuccess {nextComicId ->
            localWriteRepository.markComicRead(comicApiId,seriesApiId,nextComicId).onSuccess {
                loadComicScreen(comicApiId)
            }
        }
    }
    private fun markAsUnreadComic(comicApiId: Int, seriesApiId: Int,number:String) = viewModelScope.launch {
        async {
            remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await().onSuccess {prevComicId ->
            localWriteRepository.markComicUnread(comicApiId,seriesApiId,prevComicId).onSuccess {
                loadComicScreen(comicApiId)
            }
        }
    }
}