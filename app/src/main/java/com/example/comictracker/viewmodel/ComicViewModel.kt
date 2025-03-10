package com.example.comictracker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.model.StatisticsforAll
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.mvi.AboutComicScreenData
import com.example.comictracker.mvi.AboutSeriesScreenData
import com.example.comictracker.mvi.ComicAppIntent
import com.example.comictracker.mvi.ComicAppState
import com.example.comictracker.mvi.DataState
import com.example.comictracker.mvi.HomeScreenData
import com.example.comictracker.mvi.MyLibraryScreenData
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
            ComicAppIntent.LoadLibraryScreen -> loadProfileScreen()
            ComicAppIntent.LoadSearchScreen -> loadSearchScreen()
            is ComicAppIntent.LoadSeriesScreen -> loadSeriesScreen(intent.seriesId)
            is ComicAppIntent.MarkAsCurrentlyReadingSeries -> TODO()
            is ComicAppIntent.MarkAsReadComic -> TODO()
            is ComicAppIntent.MarkAsReadSeries -> TODO()
            is ComicAppIntent.MarkAsUnreadComic -> TODO()
            is ComicAppIntent.MarkAsUnreadSeries -> TODO()
            is ComicAppIntent.MarkAsWillBeReadSeries -> TODO()
            is ComicAppIntent.Search -> loadSearchResultsScreen(intent.query)
            is ComicAppIntent.LoadComicFromSeriesScreen -> loadComicFromSeriesScreen(intent.seriesId,intent.loadCount)
            is ComicAppIntent.LoadAllScreen -> loadAll(intent.sourceId,intent.sourceCat,intent.loadedCount)
            is ComicAppIntent.LoadAllCharactersScreen -> loadAllCharactersScreen(intent.loadedCount)
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
                val mayLikeSeriesFromBD = listOf(38809,38806,38865)
                val mayLikeSeriesDef = mayLikeSeriesFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getSeriesById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val mayLikeSeries = mayLikeSeriesDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(mayLikeSeries))
            }
            "nextComics" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsNextReadComicFromBD = listOf(113894)
                val nextComicsDef = loadedIdsNextReadComicFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getComicById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val nextComics = nextComicsDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(nextComics))
            }
            "newComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsSeriesFromBD = listOf(38809,38806,38865)
                val newComicsDef = loadedIdsSeriesFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getSeriesLastReleasesById(id)
                        } catch (e: Exception) {
                            emptyList()
                        }
                    }
                }
                val newComics = newComicsDef.awaitAll().flatten()
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(newComics))
            }
            "lastComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val lastComicsFromBD = listOf(113894)
                val lastComicsDef = lastComicsFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getComicById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val lastComics = lastComicsDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(lastComics))

            }
            "currentReading" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val currentSeriesFromBD = listOf(38809,38806,38865)
                val currentSeriesDef = currentSeriesFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getSeriesById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val currentSeries = currentSeriesDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(currentSeries))
            }
            "allLibComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val allComicsFromBD = listOf(113894)
                val allComicsDef = allComicsFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getComicById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val allComics = allComicsDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(allComics))
            }
            "allLibSeries" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val allSeriesFromBD = listOf(38809,38806,38865)
                val allSeriesDef = allSeriesFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getSeriesById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val allSeries = allSeriesDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(allSeries))
            }
            "readlist" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val readlistFromBD = listOf(38809,38806,38865)
                val readlistDef = readlistFromBD.map { id ->
                    async(Dispatchers.IO) {
                        try {
                            remoteComicRepository.getSeriesById(id)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                val readlist = readlistDef.awaitAll().filterNotNull()
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(readlist))
            }
        }
    }

    private fun loadSearchResultsScreen(query: String) = viewModelScope.launch{
        _state.value = ComicAppState.SearchResultScreenSate(DataState.Loading)
        val searchSeriesListDeferred = async(Dispatchers.IO) {
            try {
                DataState.Success(remoteComicRepository.getSeriesByTitle(query))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading results series")
            }

        }

        val searchCharacterListDeferred = async(Dispatchers.IO) {
            try {
                DataState.Success(remoteComicRepository.getCharactersByName(query))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading results characters")
            }

        }

        val seriesList = searchSeriesListDeferred.await()
        val characterList = searchCharacterListDeferred.await()

        _state.value = ComicAppState.SearchResultScreenSate(characterList,seriesList)
    }

    private fun loadComicFromSeriesScreen(seriesId: Int,loadedCount: Int)  = viewModelScope.launch{
        _state.value = ComicAppState.AllComicSeriesScreenState(DataState.Loading)
        try {
            withContext(Dispatchers.IO){
                _state.emit(
                    ComicAppState.AllComicSeriesScreenState(
                        DataState.Success(remoteComicRepository.getComicsFromSeries(seriesId,loadedCount))
                    )
                )
            }
        }catch (e:Exception){
            _state.value = ComicAppState.AllComicSeriesScreenState(
                DataState.Error("Error loading comic from this series : $e"))
        }

    }

    private fun loadHomeScreen() = viewModelScope.launch {
        _state.value = ComicAppState.HomeScreenState(DataState.Loading)

        val loadedIdsSeriesFromBD = listOf(38809,38806,38865)
        val loadedIdsNextReadComicFromBD = listOf(113894)

        val newComicsDef = loadedIdsSeriesFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getSeriesLastReleasesById(id)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

        val nextComicsDef = loadedIdsNextReadComicFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                        remoteComicRepository.getComicById(id)
                    } catch (e: Exception) {
                        null
                    }
            }
        }
        val newComics = newComicsDef.awaitAll().flatten()
        val nextComics = nextComicsDef.awaitAll().filterNotNull()
        _state.value = ComicAppState.HomeScreenState(DataState.Success(HomeScreenData(
           newReleasesList = newComics, continueReadingList = nextComics
        )))
    }

    private fun loadProfileScreen() = viewModelScope.launch {
        _state.value = ComicAppState.MyLibraryScreenState(DataState.Loading)
        val loadedStatisticsFromBD = StatisticsforAll(100,110,
            120,130,140)

        val loadedFavoriteSeriesIdsFromBD = listOf(38809,38806,38865)
        val loadedCurrentlyReadingSeriesIdsFromBD = listOf(38809,38806,38865)
        val loadedHistoryReadComicFromBD = listOf(113894)

        val favoriteSeriesDef = loadedFavoriteSeriesIdsFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getSeriesById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }
        val currentSeriesDef = loadedCurrentlyReadingSeriesIdsFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getSeriesById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }

        val lastComicsDef = loadedHistoryReadComicFromBD.map { id ->
            async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getComicById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }

        val favoriteSeries = favoriteSeriesDef.awaitAll().filterNotNull()
        val currentSeries = currentSeriesDef.awaitAll().filterNotNull()
        val lastComics = lastComicsDef.awaitAll().filterNotNull()

        _state.value = ComicAppState.MyLibraryScreenState(DataState.Success(MyLibraryScreenData(
            statistics = loadedStatisticsFromBD,
            favoritesList = favoriteSeries,
            currentlyReadingList = currentSeries,
            lastUpdates = lastComics
        )))


    }

    private fun loadSearchScreen() = viewModelScope.launch {
        _state.value = ComicAppState.SearchScreenState(DataState.Loading)
        val discoverSeriesListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteComicRepository.getAllSeries())
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading Discover Series")
            }
        }
        val mayLikeSeriesListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteComicRepository.getAllSeries())
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading May Like Series")
            }
        }
        val characterListDef  = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteComicRepository.getAllCharacters())
            }catch(e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading characters")
            }
        }

        val dseries = discoverSeriesListDef.await()
        val mlsreis = mayLikeSeriesListDef.await()
        val characters = characterListDef.await()

        _state.value = ComicAppState.SearchScreenState(
            mlsreis,dseries,characters
        )
    }


    private fun loadSeriesScreen(seriesId: Int)  = viewModelScope.launch {
        _state.value = ComicAppState.AboutComicScreenState(DataState.Loading)
        val seriesDeferred = async(Dispatchers.IO){
            try {
                remoteComicRepository.getSeriesById(seriesId)
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                emptyList<SeriesModel>()
            }
        }

        val comicListDeferred = async(Dispatchers.IO) {
            try{
                remoteComicRepository.getComicsFromSeries(seriesId)
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                emptyList()
            }
        }

        val characterListDeferred = async(Dispatchers.IO) {
            try{
                remoteComicRepository.getSeriesCharacters(seriesId)
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                emptyList()
            }
        }

        val series = seriesDeferred.await() // Получение series до зависимых задач.

        val creatorListDeferred = async(Dispatchers.IO) {
            try {
                if (series is SeriesModel){
                    remoteComicRepository.getSeriesCreators(series.creators?: emptyList())
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
                    remoteComicRepository.getConnectedSeries(series.connectedSeries)
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
                is SeriesModel -> DataState.Success(AboutSeriesScreenData(
                    series = series,
                    comicList = comicList,
                    creatorList = creatorList,
                    characterList = characterList,
                    connectedSeriesList = connectedSeriesList
                ))
                else -> DataState.Error("Error loading this series ")
            }
        )
    }

    private fun loadComicScreen(comicId: Int) = viewModelScope.launch {
        _state.value = ComicAppState.AboutComicScreenState(DataState.Loading)
        val comicDeferred = async(Dispatchers.IO){
            try {
                remoteComicRepository.getComicById(comicId)
            }catch (e:Exception){
                emptyList<ComicModel>()
            }

        }

        val characterListDeferred = async(Dispatchers.IO) {
            try {
                remoteComicRepository.getComicCharacters(comicId)
            }catch (e:Exception){
                emptyList()
            }

        }

        val comic = comicDeferred.await() // Получение series до зависимых задач.

        val creatorListDeferred = async(Dispatchers.IO) {
            try {
                if (comic is ComicModel){
                    remoteComicRepository.getComicCreators(comic.creators)
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
                is ComicModel -> DataState.Success(AboutComicScreenData(
                    comic = comic, creatorList = creatorList, characterList = characterList
                ))
                else ->  DataState.Error("Error loading this series")
            }))
    }

    private fun loadCharacterScreen(characterId:Int) = viewModelScope.launch {
        _state.value = ComicAppState.AboutCharacterScreenState(
            character = DataState.Loading,
            series = DataState.Loading
        )
        val characterDef = async(Dispatchers.IO) {
            try {
                DataState.Success(remoteComicRepository.getCharacterById(characterId))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading character")
            }

        }

        val seriesDef = async(Dispatchers.IO) {
            try{
                DataState.Success(remoteComicRepository.getCharacterSeries(characterId))
            }catch (e:Exception){
                Log.e("ViewModel","$e")
                DataState.Error("Error loading comics with this character")
            }
        }

        val character = characterDef.await()

        _state.value = ComicAppState.AboutCharacterScreenState(
            character =  character,
            series = DataState.Loading
        )

        _state.value = ComicAppState.AboutCharacterScreenState(
            character =  character,
            series = seriesDef.await()
        )
    }
}