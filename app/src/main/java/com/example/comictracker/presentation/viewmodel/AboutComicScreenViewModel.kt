package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.LocalComicRepository
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.presentation.mvi.AboutComicScreenData
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AboutComicScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val localComicRepository: LocalComicRepository,
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
        val comicDeferred = async(Dispatchers.IO){
            try {
                remoteComicsRepository.getComicById(comicId)
            }catch (e:Exception){
                emptyList<ComicModel>()
            }

        }

        val characterListDeferred = async(Dispatchers.IO) {
            try {
                remoteCharacterRepository.getComicCharacters(comicId)
            }catch (e:Exception){
                emptyList()
            }

        }

        val comic = comicDeferred.await()


        val creatorListDeferred = async(Dispatchers.IO) {
            try {
                if (comic is ComicModel){
                    remoteCreatorsRepository.getComicCreators(comic.creators)
                } else {
                    emptyList()
                }
            }catch (e:Exception){
                emptyList()
            }

        }

        val characterList = characterListDeferred.await()
        val creatorList = creatorListDeferred.await()

        _state.value = (ComicAppState.AboutComicScreenState(
            when(comic){
                is ComicModel ->{
                    val readMark = localComicRepository.loadComicMark(comic.comicId)
                    val comicWithMark = comic.copy(readMark = readMark)
                    DataState.Success(
                        AboutComicScreenData(
                            comic = comicWithMark, creatorList = creatorList, characterList = characterList)
                    )
                }

                else ->  DataState.Error("Error loading this series")
            }))
    }

    private fun markAsReadComic(comicApiId: Int, seriesApiId: Int,number:String)  = viewModelScope.launch{
        val nextComicId = async {
            remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await()
        if(localComicRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){
            loadComicScreen(comicApiId)
        }
    }
    private fun markAsUnreadComic(comicApiId: Int, seriesApiId: Int,number:String) = viewModelScope.launch {
        val prevComicId = async {
            remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await()
        if(localComicRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
            loadComicScreen(comicApiId)
        }
    }
}