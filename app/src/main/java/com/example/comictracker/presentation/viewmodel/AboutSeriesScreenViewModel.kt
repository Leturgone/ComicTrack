package com.example.comictracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.AboutSeriesUseCases
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
    private val aboutSeriesUseCases: AboutSeriesUseCases
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
        val seriesDeferred = async{
            aboutSeriesUseCases.loadSeriesDataUseCase(seriesId)
        }

        val comicListDeferred = async {
            aboutSeriesUseCases.loadComicsListUseCase(seriesId)
        }

        val characterListDeferred = async {
            aboutSeriesUseCases.loadSeriesCharactersUseCase(seriesId)
        }

        val series = seriesDeferred.await().fold(
            onSuccess = {it},
            onFailure = { null}
        ) // Получение series до зависимых задач.

        if (series!=null){

            val creatorListDeferred = async {
                aboutSeriesUseCases.loadSeriesCreatorsUseCase(series)
            }

            val connectedSeriesListDeferred = async {
                aboutSeriesUseCases.loadConnectedSeriesUseCase(series)
            }

            val comicList = comicListDeferred.await().fold(
                onSuccess = {it},
                onFailure = { emptyList() }
            )

            val nextReadLocDef = async {
                aboutSeriesUseCases.loadNextComicInSeriesUseCase(comicList,seriesId)
            }


            val characterList = characterListDeferred.await().fold(
                onSuccess = {it},
                onFailure = { emptyList() }
            )

            val creatorList = creatorListDeferred.await().fold(
                onSuccess = {it},
                onFailure = { emptyList() }
            )

            val connectedSeriesList = connectedSeriesListDeferred.await().fold(
                onSuccess = {it},
                onFailure = { emptyList() }
            )
            val nextReadComic = nextReadLocDef.await().fold(
                onSuccess = {it},
                onFailure = {null}
            )
            _state.value = ComicAppState.AboutSeriesScreenState(
                DataState.Success(
                    AboutSeriesScreenData(
                        series = series,
                        comicList = comicList,
                        creatorList = creatorList,
                        characterList = characterList,
                        connectedSeriesList = connectedSeriesList,
                        nextRead = nextReadComic
                    )
                )
            )
        }
        else {
            _state.value = ComicAppState.AboutSeriesScreenState(
                DataState.Error("Error loading this series ")
            )
        }
    }

    private fun markAsCurrentlyReadingSeries(apiId:Int,firstIssueId:Int?)  = viewModelScope.launch{
        aboutSeriesUseCases.markSeriesAsCurrentlyReadingUseCase(apiId, firstIssueId).onSuccess {
            loadSeriesScreen(apiId)
        }
    }

    private fun markAsReadSeries(apiId:Int) = viewModelScope.launch(Dispatchers.IO){
        aboutSeriesUseCases.markSeriesAsReadUseCase(apiId).onSuccess {
            loadSeriesScreen(apiId)
        }
    }

    private fun markUnreadSeries(apiId:Int)  = viewModelScope.launch{
        aboutSeriesUseCases.markSeriesAsUnreadUseCase(apiId).onSuccess {
            loadSeriesScreen(apiId)
        }
    }

    private fun markWillBeReadSeries(apiId:Int)  = viewModelScope.launch{
        aboutSeriesUseCases.markSeriesAsWillBeReadUseCase(apiId).onSuccess {
            loadSeriesScreen(apiId)
        }
    }

    private fun addSeriesToFavorite(apiId: Int) = viewModelScope.launch {
        aboutSeriesUseCases.addSeriesToFavoritesUseCase(apiId).onSuccess {
            loadSeriesScreen(apiId)
        }
    }

    private fun removeSeriesFromFavorites(apiId: Int)  = viewModelScope.launch{
       aboutSeriesUseCases.removeSeriesFromFavoritesUseCase(apiId).onSuccess {
            loadSeriesScreen(apiId)
        }

    }

    private fun markAsReadNextComic(comicApiId: Int, seriesApiId: Int, number: String) = viewModelScope.launch{
        aboutSeriesUseCases.markNextComicAsReadUseCase(comicApiId, seriesApiId, number).onSuccess {
            loadSeriesScreen(seriesApiId)
        }
    }

    private fun markAsUnreadNextComic(comicApiId: Int, seriesApiId: Int, number: String) = viewModelScope.launch{
        aboutSeriesUseCases.markNextComicAsUnreadUseCase(comicApiId, seriesApiId, number).onSuccess {
            loadSeriesScreen(seriesApiId)
        }
    }
}