package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
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
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val remoteComicsRepository: RemoteComicsRepository,
    private val remoteCharacterRepository: RemoteCharacterRepository,
    private val localReadRepository: LocalReadRepository
): ViewModel(){

    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())
    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent:AllScreenIntent){
        when(intent){
            is AllScreenIntent.LoadAllCharactersScreen -> loadAllCharactersScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllScreen -> loadAll(intent.sourceId,intent.sourceCat,intent.loadedCount)
            is AllScreenIntent.LoadAllCharacterSeriesScreen -> loadAllCharacterSeriesScreen(intent.characterId,intent.loadedCount)
            is AllScreenIntent.LoadAllCurrentReadingSeriesScreen -> loadAllCurrentReadingSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllDiscoverSeriesScreen -> loadAllDiscoverSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllLastComicScreen -> loadAllLastComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllLibComicScreen -> loadAllLibComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllLibSeriesScreen -> loadAllLibSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllMayLikeSeriesScreen -> loadAllMayLikeSeriesScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllNewComicScreen -> loadAllNewComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadAllNextComicScreen -> loadAllNextComicScreen(intent.loadedCount)
            is AllScreenIntent.LoadReadListScreen -> loadReadListScreen(intent.loadedCount)
        }
    }

    private fun loadAllCharacterSeriesScreen(characterId: Int,loadedCount:Int) = viewModelScope.launch{
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val seriesDef = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteSeriesRepository.getCharacterSeries(characterId,loadedCount))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading comics with this character")
            }
        }
        val series = seriesDef.await()
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }

    private fun loadAllCurrentReadingSeriesScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val currentSeriesFromBD = localReadRepository.loadCurrentReadIds(loadedCount)
        val currentSeries = remoteSeriesRepository.fetchSeries(currentSeriesFromBD)
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(currentSeries))
    }

    private fun loadAllDiscoverSeriesScreen(loadedCount:Int) = viewModelScope.launch{
        _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
        val seriesDef = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteSeriesRepository.getAllSeries(loadedCount))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading comics with this character")
            }
        }
        val series = seriesDef.await()
        _state.value = ComicAppState.AllSeriesScreenSate(series)
    }


    private fun loadAllCharactersScreen(loadedCount: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllCharactersScreenSate(DataState.Loading)
        val characterListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteCharacterRepository.getAllCharacters(loadedCount))
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading characters")
            }
        }
        val characters = characterListDef.await()
        _state.value = ComicAppState.AllCharactersScreenSate(characters)
    }

    private fun loadAllLastComicScreen(loadedCount:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
        val lastComicsFromBD = localReadRepository.loadHistory(loadedCount)
        val lastComics = remoteComicsRepository.fetchComics(lastComicsFromBD)
        _state.value = ComicAppState.AllComicScreenSate(DataState.Success(lastComics))
    }

    private fun loadAll(sourceId: Int, sourceCat: String, loadedCount: Int) = viewModelScope.launch {

        when(sourceCat){
//            "characterSeries" ->{
//                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
//                val seriesDef = async(Dispatchers.IO) {
//                    try{
//                        DataState.Success(remoteSeriesRepository.getCharacterSeries(sourceId,loadedCount))
//                    }catch (e:Exception){
//                        Log.e("ViewModel","$e")
//                        DataState.Error("Error loading comics with this character")
//                    }
//                }
//                val series = seriesDef.await()
//                _state.value = ComicAppState.AllSeriesScreenSate(series)
//
//            }
//            "discover" ->{
//                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
//                val seriesDef = async(Dispatchers.IO) {
//                    try{
//                        DataState.Success(remoteSeriesRepository.getAllSeries(loadedCount))
//                    }catch (e:Exception){
//                        Log.e("ViewModel","$e")
//                        DataState.Error("Error loading comics with this character")
//                    }
//                }
//                val series = seriesDef.await()
//                _state.value = ComicAppState.AllSeriesScreenSate(series)
//            }
            "mayLike" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val loadedIdsSeriesFromBD = localReadRepository.loadAllReadSeriesIds(loadedCount)
                val mayLikeSeriesIds = remoteSeriesRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD)
                val mayLikeSeries = remoteSeriesRepository.fetchSeries(mayLikeSeriesIds)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(mayLikeSeries))
            }
            "nextComics" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsNextReadComicFromBD = localReadRepository.loadNextReadComicIds(loadedCount)
                val nextComics = remoteComicsRepository.fetchComics(loadedIdsNextReadComicFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(nextComics))
            }
            "newComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsSeriesFromBD = localReadRepository.loadCurrentReadIds(loadedCount)
                val newComics = remoteComicsRepository.fetchUpdatesForSeries(loadedIdsSeriesFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(newComics))
            }
//            "lastComic" ->{
//                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
//                val lastComicsFromBD = localReadRepository.loadHistory(loadedCount)
//                val lastComics = remoteComicsRepository.fetchComics(lastComicsFromBD)
//                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(lastComics))
//
//            }
//            "currentReading" ->{
//                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
//                val currentSeriesFromBD = localReadRepository.loadCurrentReadIds(loadedCount)
//                val currentSeries = remoteSeriesRepository.fetchSeries(currentSeriesFromBD)
//                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(currentSeries))
//            }
            "allLibComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val allComicsFromBD = localReadRepository.loadAllReadComicIds(loadedCount)
                val allComics = remoteComicsRepository.fetchComics(allComicsFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(allComics))
            }
            "allLibSeries" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val allSeriesFromBD = localReadRepository.loadAllReadSeriesIds(loadedCount)
                val allSeries = remoteSeriesRepository.fetchSeries(allSeriesFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(allSeries))
            }
            "readlist" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val readlistFromBD = localReadRepository.loadWillBeReadIds(loadedCount)
                val readlist = remoteSeriesRepository.fetchSeries(readlistFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(readlist))
            }
        }
    }
}