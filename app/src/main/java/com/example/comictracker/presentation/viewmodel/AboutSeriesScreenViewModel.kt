package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.LocalComicRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.presentation.mvi.AboutSeriesScreenData
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AboutSeriesScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutSeriesScreenViewModel @Inject constructor(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val remoteComicsRepository:RemoteComicsRepository,
    private val remoteCharacterRepository: RemoteCharacterRepository,
    private val remoteCreatorsRepository: RemoteCreatorsRepository,
    private val localComicRepository: LocalComicRepository,
): ViewModel(){

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:AboutSeriesScreenIntent){
        when(intent){
            is AboutSeriesScreenIntent.AddSeriesToFavorite -> addSeriesToFavorite(intent.seriesId)
            is AboutSeriesScreenIntent.LoadSeriesScreen -> loadSeriesScreen(intent.seriesId)
            is AboutSeriesScreenIntent.MarkAsCurrentlyReadingSeries -> markAsCurrentlyReadingSeries(intent.seriesId,intent.firstIssueId)
            is AboutSeriesScreenIntent.MarkAsReadSeries -> markAsReadSeries(intent.seriesId)
            is AboutSeriesScreenIntent.MarkAsUnreadSeries -> markUnreadSeries(intent.seriesId)
            is AboutSeriesScreenIntent.MarkAsWillBeReadSeries -> markWillBeReadSeries(intent.seriesId)
            is AboutSeriesScreenIntent.RemoveSeriesFromFavorite -> removeSeriesFromFavorites(intent.seriesId)
            is AboutSeriesScreenIntent.MarkAsReadNextComic -> markAsReadNextComic(intent.comicId,intent.seriesId, intent.issueNumber)
            is AboutSeriesScreenIntent.MarkAsUnreadLastComic -> markAsUnreadNextComic(intent.comicId,intent.seriesId, intent.issueNumber)
        }
    }

    private fun loadSeriesScreen(seriesId: Int)  = viewModelScope.launch {
        _state.value = ComicAppState.AboutComicScreenState(DataState.Loading)
        val seriesDeferred = async(Dispatchers.IO){
            try {
                remoteSeriesRepository.getSeriesById(seriesId)
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                emptyList<SeriesModel>()
            }
        }

        val comicListDeferred = async(Dispatchers.IO) {
            try{
                remoteComicsRepository.getComicsFromSeries(seriesId)
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                emptyList()
            }
        }

        val characterListDeferred = async(Dispatchers.IO) {
            try{
                remoteCharacterRepository.getSeriesCharacters(seriesId)
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                emptyList()
            }
        }

        val series = seriesDeferred.await() // Получение series до зависимых задач.

        val creatorListDeferred = async(Dispatchers.IO) {
            try {
                if (series is SeriesModel){
                    remoteCreatorsRepository.getSeriesCreators(series.creators?: emptyList())
                } else {
                    emptyList()
                }
            }catch (e:Exception){
                emptyList()
            }

        }

        val connectedSeriesListDeferred = async(Dispatchers.IO) {
            try{
                if (series is SeriesModel){
                    remoteSeriesRepository.getConnectedSeries(series.connectedSeries)
                }else{
                    emptyList()
                }
            }catch (e:Exception){
                emptyList()
            }

        }

        val comicList = comicListDeferred.await()
        val characterList = characterListDeferred.await()
        val creatorList = creatorListDeferred.await()
        val connectedSeriesList = connectedSeriesListDeferred.await()

        _state.value = ComicAppState.AboutSeriesScreenState(
            when(series){
                is SeriesModel -> {
                    val readMark = localComicRepository.loadSeriesMark(series.seriesId)
                    val favoriteMark = localComicRepository.loadSeriesFavoriteMark(series.seriesId)
                    val nextRead = localComicRepository.loadNextRead(series.seriesId)?.let {
                        remoteComicsRepository.getComicById(it)
                    }
                    Log.i("ViewModel",favoriteMark.toString())
                    val seriesWithMark = series.copy(readMark = readMark, favoriteMark = favoriteMark)
                    DataState.Success(
                        AboutSeriesScreenData(
                            series = seriesWithMark,
                            comicList = comicList,
                            creatorList = creatorList,
                            characterList = characterList,
                            connectedSeriesList = connectedSeriesList,
                            nextRead = nextRead?: if (comicList.isNotEmpty()) comicList[0] else null
                        )
                    )
                }
                else -> DataState.Error("Error loading this series ")
            }
        )
    }

    private fun markAsCurrentlyReadingSeries(apiId:Int,firstIssueId:Int?)  = viewModelScope.launch{
        if (localComicRepository.addSeriesToCurrentlyRead(apiId,firstIssueId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun markAsReadSeries(apiId:Int) = viewModelScope.launch(Dispatchers.IO){
        if (localComicRepository.markSeriesRead(apiId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun markUnreadSeries(apiId:Int)  = viewModelScope.launch{
        if (localComicRepository.markSeriesUnread(apiId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun markWillBeReadSeries(apiId:Int)  = viewModelScope.launch{
        if(localComicRepository.addSeriesToWillBeRead(apiId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun addSeriesToFavorite(apiId: Int) = viewModelScope.launch {
        if(localComicRepository.addSeriesToFavorite(apiId)){
            loadSeriesScreen(apiId)
        }

    }

    private fun removeSeriesFromFavorites(apiId: Int)  = viewModelScope.launch{
        if(localComicRepository.removeSeriesFromFavorite(apiId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun markAsReadNextComic(comicApiId: Int, seriesApiId: Int, number: String) = viewModelScope.launch{
        val nextComicId = async {
            remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await()

        if (localComicRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){
            loadSeriesScreen(seriesApiId)
        }
    }

    private fun markAsUnreadNextComic(comicApiId: Int, seriesApiId: Int, number: String) = viewModelScope.launch{
        val prevComicId = async {
            remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await()

        if (localComicRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
            loadSeriesScreen(seriesApiId)
        }
    }
}