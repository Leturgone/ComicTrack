package com.example.comictracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.LocalComicRepository
import com.example.comictracker.domain.repository.RemoteComicRepository
import com.example.comictracker.presentation.mvi.AboutComicScreenData
import com.example.comictracker.presentation.mvi.AboutSeriesScreenData
import com.example.comictracker.presentation.mvi.ComicAppIntent
import com.example.comictracker.presentation.mvi.ComicAppState
import com.example.comictracker.presentation.mvi.DataState
import com.example.comictracker.presentation.mvi.HomeScreenData
import com.example.comictracker.presentation.mvi.MyLibraryScreenData
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
    private val remoteComicRepository: RemoteComicRepository,
    private val localComicRepository: LocalComicRepository): ViewModel(){

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
            is ComicAppIntent.MarkAsCurrentlyReadingSeries -> markAsCurrentlyReadingSeries(intent.seriesId,intent.firstIssueId)
            is ComicAppIntent.MarkAsReadComic -> markAsReadComic(intent.comicId,intent.seriesId, intent.issueNumber)
            is ComicAppIntent.MarkAsReadSeries -> markAsReadSeries(intent.seriesId)
            is ComicAppIntent.MarkAsUnreadComic -> markAsUnreadComic(intent.comicId,intent.seriesId,intent.issueNumber)
            is ComicAppIntent.MarkAsUnreadSeries -> markUnreadSeries(intent.seriesId)
            is ComicAppIntent.MarkAsWillBeReadSeries -> markWillBeReadSeries(intent.seriesId)
            is ComicAppIntent.Search -> loadSearchResultsScreen(intent.query)
            is ComicAppIntent.LoadComicFromSeriesScreen -> loadComicFromSeriesScreen(intent.seriesId,intent.loadCount)
            is ComicAppIntent.LoadAllScreen -> loadAll(intent.sourceId,intent.sourceCat,intent.loadedCount)
            is ComicAppIntent.LoadAllCharactersScreen -> loadAllCharactersScreen(intent.loadedCount)
            is ComicAppIntent.AddSeriesToFavorite -> addSeriesToFavorite(intent.seriesId)
            is ComicAppIntent.RemoveSeriesFromFavorite -> removeSeriesFromFavorites(intent.seriesId)
            is ComicAppIntent.MarkAsReadComicInList -> markAsReadComicInList(intent.comicId,intent.seriesId, intent.issueNumber,intent.loadedCount)
            is ComicAppIntent.MarkAsUnreadComicInList -> markAsUnreadComicInList(intent.comicId,intent.seriesId, intent.issueNumber,intent.loadedCount)
        }

    }




    private suspend fun fetchComics(ids: List<Int>): List<ComicModel> {
        val comicsDef = ids.map { id ->
            viewModelScope.async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getComicById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }
        return comicsDef.awaitAll().filterNotNull()
    }
    private suspend fun fetchSeries(ids: List<Int>): List<SeriesModel> {
        val seriesDef = ids.map { id ->
            viewModelScope.async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getSeriesById(id)
                } catch (e: Exception) {
                    null
                }
            }
        }
        return seriesDef.awaitAll().filterNotNull()
    }
    private suspend fun fetchUpdatesForSeries(ids: List<Int>): List<ComicModel> {
        val newComicsDef = ids.map { id ->
            viewModelScope.async(Dispatchers.IO) {
                try {
                    remoteComicRepository.getSeriesLastReleasesById(id)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
        return newComicsDef.awaitAll().flatten()
    }

    private fun markAsReadSeries(apiId:Int) = viewModelScope.launch{
        if (localComicRepository.markSeriesRead(apiId)){
            loadSeriesScreen(apiId)
        }
    }
    private fun markWillBeReadSeries(apiId:Int)  = viewModelScope.launch{
        if(localComicRepository.addSeriesToWillBeRead(apiId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun markUnreadSeries(apiId:Int)  = viewModelScope.launch{
        if (localComicRepository.markSeriesUnread(apiId)){
            loadSeriesScreen(apiId)
        }
    }

    private fun markAsCurrentlyReadingSeries(apiId:Int,firstIssueId:Int?)  = viewModelScope.launch{
        if (localComicRepository.addSeriesToCurrentlyRead(apiId,firstIssueId)){
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
    private fun markAsReadComic(comicApiId: Int, seriesApiId: Int,number:String)  = viewModelScope.launch{
        val nextComicId = async {
            remoteComicRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await()
        if(localComicRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){
            loadComicScreen(comicApiId)
        }
    }

    private fun markAsUnreadComic(comicApiId: Int, seriesApiId: Int,number:String) = viewModelScope.launch {
        val prevComicId = async {
            remoteComicRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await()
        if(localComicRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
            loadComicScreen(comicApiId)
        }
    }

    private fun markAsUnreadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int)  = viewModelScope.launch{
        val prevComicId = async {
            remoteComicRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        }.await()

        if (localComicRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)){
            loadComicFromSeriesScreen(seriesApiId,loadedCount)
        }
    }

    private fun markAsReadComicInList(comicApiId: Int, seriesApiId: Int, number: String, loadedCount: Int?) = viewModelScope.launch{
        val nextComicId = async {
            remoteComicRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        }.await()

        if (localComicRepository.markComicRead(comicApiId,seriesApiId,nextComicId)){

            if(loadedCount==null){
                loadSeriesScreen(seriesApiId)
            }else{
                loadComicFromSeriesScreen(seriesApiId,loadedCount)
            }

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
                val mayLikeSeries = fetchSeries(mayLikeSeriesIds)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(mayLikeSeries))
            }
            "nextComics" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsNextReadComicFromBD = localComicRepository.loadNextReadComicIds(loadedCount)
                val nextComics = fetchComics(loadedIdsNextReadComicFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(nextComics))
            }
            "newComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val loadedIdsSeriesFromBD = localComicRepository.loadCurrentReadIds(loadedCount)
                val newComics = fetchUpdatesForSeries(loadedIdsSeriesFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(newComics))
            }
            "lastComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val lastComicsFromBD = localComicRepository.loadHistory(loadedCount)
                val lastComics = fetchComics(lastComicsFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(lastComics))

            }
            "currentReading" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val currentSeriesFromBD = localComicRepository.loadCurrentReadIds(loadedCount)
                val currentSeries = fetchSeries(currentSeriesFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(currentSeries))
            }
            "allLibComic" ->{
                _state.value = ComicAppState.AllComicScreenSate(DataState.Loading)
                val allComicsFromBD = localComicRepository.loadAllReadComicIds(loadedCount)
                val allComics = fetchComics(allComicsFromBD)
                _state.value = ComicAppState.AllComicScreenSate(DataState.Success(allComics))
            }
            "allLibSeries" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val allSeriesFromBD = localComicRepository.loadAllReadSeriesIds(loadedCount)
                val allSeries = fetchSeries(allSeriesFromBD)
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Success(allSeries))
            }
            "readlist" ->{
                _state.value = ComicAppState.AllSeriesScreenSate(DataState.Loading)
                val readlistFromBD = localComicRepository.loadWillBeReadIds(loadedCount)
                val readlist = fetchSeries(readlistFromBD)
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
                        DataState.Success(
                            remoteComicRepository.getComicsFromSeries(seriesId,loadedCount).map {
                                val readMark = localComicRepository.loadComicMark(it.comicId)
                                it.copy(readMark = readMark)
                            })
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

        val loadedIdsSeriesFromBD = localComicRepository.loadCurrentReadIds(0)
        val loadedIdsNextReadComicFromBD = localComicRepository.loadNextReadComicIds(0)

        val newComics = fetchUpdatesForSeries(loadedIdsSeriesFromBD)
        val nextComics = fetchComics(loadedIdsNextReadComicFromBD)

        _state.value = ComicAppState.HomeScreenState(
            DataState.Success(
                HomeScreenData(
           newReleasesList = newComics, continueReadingList = nextComics
        )
            ))
    }

    private fun loadProfileScreen() = viewModelScope.launch {
        _state.value = ComicAppState.MyLibraryScreenState(DataState.Loading)

        val loadedStatisticsFromBD = localComicRepository.loadStatistics()
        val loadedFavoriteSeriesIdsFromBD = localComicRepository.loadFavoritesIds()
        val loadedCurrentlyReadingSeriesIdsFromBD = localComicRepository.loadCurrentReadIds(0)
        val loadedHistoryReadComicFromBD = localComicRepository.loadHistory(0)

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

        _state.value = ComicAppState.MyLibraryScreenState(
            DataState.Success(
                MyLibraryScreenData(
            statistics = loadedStatisticsFromBD,
            favoritesList = favoriteSeries,
            currentlyReadingList = currentSeries,
            lastUpdates = lastComics
        )
            ))


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
                val loadedIdsSeriesFromBD = localComicRepository.loadAllReadSeriesIds(0)
                val mayLikeSeries= remoteComicRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD)
                DataState.Success(fetchSeries(mayLikeSeries))
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
                is SeriesModel -> {
                    val readMark = localComicRepository.loadSeriesMark(series.seriesId)
                    val favoriteMark = localComicRepository.loadSeriesFavoriteMark(series.seriesId)
                    val nextRead = localComicRepository.loadNextRead(series.seriesId)?.let {
                        remoteComicRepository.getComicById(it)
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

        val comic = comicDeferred.await()


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