package com.example.comictracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.mvi.AboutSeriesScreenData
import com.example.comictracker.mvi.ComicAppIntent
import com.example.comictracker.mvi.ComicAppState
import com.example.comictracker.mvi.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ComicViewModel @Inject constructor(
    private val remoteComicRepository: RemoteComicRepository):ViewModel(){

    // Начальное состояние
    private val _state = MutableStateFlow<ComicAppState>(ComicAppState.HomeScreenState())

    val state: StateFlow<ComicAppState> = _state

    fun processIntent(intent: ComicAppIntent){
        when(intent){
            is ComicAppIntent.LoadCharacterScreen -> loadCharacterScreen(intent.characterId)
            is ComicAppIntent.LoadComicScreen -> loadComicScreen(intent.comicId)
            ComicAppIntent.LoadHomeScreen -> loadHomeScreen()
            ComicAppIntent.LoadProfileScreen -> loadProfileScreen()
            ComicAppIntent.LoadSearchScreen -> loadSearchScreen()
            is ComicAppIntent.LoadSeriesScreen -> loadSeriesScreen(intent.seriesId)
            is ComicAppIntent.MarkAsCurrentlyReadingSeries -> TODO()
            is ComicAppIntent.MarkAsReadComic -> TODO()
            is ComicAppIntent.MarkAsReadSeries -> TODO()
            is ComicAppIntent.MarkAsUnreadComic -> TODO()
            is ComicAppIntent.MarkAsUnreadSeries -> TODO()
            is ComicAppIntent.MarkAsWillBeReadSeries -> TODO()
            is ComicAppIntent.Search -> TODO()
            is ComicAppIntent.LoadComicFromSeriesScreen -> loadComicFromSeriesScreen(intent.seriesId)
        }

    }

    private fun loadComicFromSeriesScreen(seriesId: Int)  = viewModelScope.launch{
        _state.value = ComicAppState.AllComicSeriesScreenState(DataState.Loading)
        try {
            withContext(Dispatchers.IO){
                _state.emit(
                    ComicAppState.AllComicSeriesScreenState(
                        DataState.Success(remoteComicRepository.getComicsFromSeries(seriesId))
                    )
                )
            }
        }catch (e:Exception){
            _state.value = ComicAppState.AllComicSeriesScreenState(
                DataState.Error("Error loading comic from this series : $e"))
        }

    }

    private fun loadHomeScreen() {
        TODO("Not yet implemented")
    }

    private fun loadProfileScreen() {
        TODO("Not yet implemented")
    }

    private fun loadSearchScreen() {
        TODO("Not yet implemented")
    }

    private fun loadSeriesScreen(seriesId: Int)  = viewModelScope.launch {
        _state.value = ComicAppState.AboutComicScreenState(DataState.Loading)

        try {
            withContext(Dispatchers.IO){
                val series =  remoteComicRepository.getSeriesById(seriesId.toString())
                _state.emit(ComicAppState.AboutSeriesScreenState(
                    DataState.Success(AboutSeriesScreenData(
                        series = series,
                        comicList = remoteComicRepository.getComicsFromSeries(seriesId),
                        creatorList = remoteComicRepository.getSeriesCreators(series.creators?: emptyList()),
                        characterList = remoteComicRepository.getSeriesCharacters(seriesId),
                        connectedSeriesList =remoteComicRepository.getConnectedSeries(series.connectedSeries)
                    ))
                ))
            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                _state.value = ComicAppState.AboutCharacterScreenState(
                    DataState.Error("Error loading this series : $e")
                )
            }
        }
    }

    private fun loadComicScreen(comicId: Int) {
        TODO("Not yet implemented")
    }

    private fun loadCharacterScreen(characterId:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AboutCharacterScreenState(
            character = DataState.Loading,
            series = DataState.Loading
        )
        try {
            withContext(Dispatchers.IO){
                _state.emit(ComicAppState.AboutCharacterScreenState(
                    character = DataState.Success(remoteComicRepository.getCharacterById(characterId)),
                    series = DataState.Success(remoteComicRepository.getCharacterSeries(characterId))
                ))

            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                _state.value = ComicAppState.AboutCharacterScreenState(
                    character = DataState.Error("Error loading character"),
                    series = DataState.Error("Error loading comics with this character")
                )
            }
        }


    }
}