package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
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
    private val localWriteRepository: LocalWriteRepository,
    private val localReadRepository: LocalReadRepository
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
                    val readMark = localReadRepository.loadSeriesMark(series.seriesId)
                    val favoriteMark = localReadRepository.loadSeriesFavoriteMark(series.seriesId)
                    val nextRead = localReadRepository.loadNextRead(series.seriesId)?.let {
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
        try {
            if (localWriteRepository.addSeriesToCurrentlyRead(apiId,firstIssueId)){
                loadSeriesScreen(apiId)
            }
        }catch (e:Exception){
            Log.e("markAsCurrentlyReadingSeries",e.toString())
        }
    }

    private fun markAsReadSeries(apiId:Int) = viewModelScope.launch(Dispatchers.IO){
        try {
            if (localWriteRepository.markSeriesRead(apiId)){
                loadSeriesScreen(apiId)
            }
        }catch (e:Exception){
            Log.e("markAsReadSeries",e.toString())
        }
    }

    private fun markUnreadSeries(apiId:Int)  = viewModelScope.launch{
        try {
            if (localWriteRepository.markSeriesUnread(apiId)){
                loadSeriesScreen(apiId)
            }
        }catch (e:Exception){
            Log.e("markUnreadSeries",e.toString())
        }
    }

    private fun markWillBeReadSeries(apiId:Int)  = viewModelScope.launch{
        try {
            if(localWriteRepository.addSeriesToWillBeRead(apiId)){
                loadSeriesScreen(apiId)
            }
        }catch (e:Exception){
            Log.e("markWillBeReadSeries",e.toString())
        }
    }

    private fun addSeriesToFavorite(apiId: Int) = viewModelScope.launch {
        try {
            if(localWriteRepository.addSeriesToFavorite(apiId)){
                loadSeriesScreen(apiId)
            }
        }catch (e:Exception){
            Log.e("addSeriesToFavorite",e.toString())
        }
    }

    private fun removeSeriesFromFavorites(apiId: Int)  = viewModelScope.launch{
        try {
            if(localWriteRepository.removeSeriesFromFavorite(apiId)){
                loadSeriesScreen(apiId)
            }
        }catch (e:Exception){
            Log.e("removeSeriesFromFavorites",e.toString())
        }

    }

    private fun markAsReadNextComic(comicApiId: Int, seriesApiId: Int, number: String) = viewModelScope.launch{
        try {
            val nextComicId = async {
                remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
            }.await()

            if (localWriteRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){
                loadSeriesScreen(seriesApiId)
            }
        }catch (e:Exception){
            Log.e("markAsReadNextComic",e.toString())
        }

    }

    private fun markAsUnreadNextComic(comicApiId: Int, seriesApiId: Int, number: String) = viewModelScope.launch{
        try {
            val prevComicId = async {
                remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
            }.await()

            if (localWriteRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
                loadSeriesScreen(seriesApiId)
            }
        }catch (e:Exception){
            Log.e("markAsUnreadNextComic",e.toString())
        }
    }
}