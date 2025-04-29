package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.LocalComicRepository
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.intents.AllScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllScreenViewModel @Inject constructor(
    private val remoteComicRepository: RemoteComicRepository,
    private val localComicRepository: LocalComicRepository,
): ViewModel(){

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:AllScreenIntent){
        when(intent){
            is AllScreenIntent.LoadAllCharactersScreen -> loadAllCharactersScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllScreen -> loadAll(intent.sourceId,intent.sourceCat,intent.loadedCount)
        }
    }


    private fun loadAllCharactersScreen(loadedCount: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllCharactersScreenSate(DataState.Loading)
        val characterListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteComicRepository.getAllCharacters(loadedCount))
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading characters")
            }
        }
        val characters = characterListDef.await()
        _state.value = ComicAppState.AllCharactersScreenSate(characters)
    }

    private fun loadAll(sourceId: Int, sourceCat: String, loadedCount: Int) = viewModelScope.launch {

        when(sourceCat){
            "characterSeries" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val seriesDef = async(Dispatchers.IO) {
                    try{
                        DataState.Success(remoteComicRepository.getCharacterSeries(sourceId,loadedCount))
                    }catch (e:Exception){
                        Log.e("ViewModel","$e")
                        DataState.Error("Error loading comics with this character")
                    }
                }
                val series = seriesDef.await()
                _state.value = ComicAppState.AllSeriesScreenSate(series)

            }
            "discover" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val seriesDef = async(Dispatchers.IO) {
                    try{
                        DataState.Success(remoteComicRepository.getAllSeries(loadedCount))
                    }catch (e:Exception){
                        Log.e("ViewModel","$e")
                        DataState.Error("Error loading comics with this character")
                    }
                }
                val series = seriesDef.await()
                _state.value = ComicAppState.AllSeriesScreenSate(series)
            }
            "mayLike" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val loadedIdsSeriesFromBD = localComicRepository.loadAllReadSeriesIds(loadedCount)
                val mayLikeSeriesIds = remoteComicRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD)
                val mayLikeSeries = remoteComicRepository.fetchSeries(mayLikeSeriesIds)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(mayLikeSeries))
            }
            "nextComics" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsNextReadComicFromBD = localComicRepository.loadNextReadComicIds(loadedCount)
                val nextComics = remoteComicRepository.fetchComics(loadedIdsNextReadComicFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(nextComics))
            }
            "newComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsSeriesFromBD = localComicRepository.loadCurrentReadIds(loadedCount)
                val newComics = remoteComicRepository.fetchUpdatesForSeries(loadedIdsSeriesFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(newComics))
            }
            "lastComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val lastComicsFromBD = localComicRepository.loadHistory(loadedCount)
                val lastComics = remoteComicRepository.fetchComics(lastComicsFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(lastComics))

            }
            "currentReading" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val currentSeriesFromBD = localComicRepository.loadCurrentReadIds(loadedCount)
                val currentSeries = remoteComicRepository.fetchSeries(currentSeriesFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(currentSeries))
            }
            "allLibComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val allComicsFromBD = localComicRepository.loadAllReadComicIds(loadedCount)
                val allComics = remoteComicRepository.fetchComics(allComicsFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(allComics))
            }
            "allLibSeries" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val allSeriesFromBD = localComicRepository.loadAllReadSeriesIds(loadedCount)
                val allSeries = remoteComicRepository.fetchSeries(allSeriesFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(allSeries))
            }
            "readlist" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val readlistFromBD = localComicRepository.loadWillBeReadIds(loadedCount)
                val readlist = remoteComicRepository.fetchSeries(readlistFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(readlist))
            }
        }
    }
}