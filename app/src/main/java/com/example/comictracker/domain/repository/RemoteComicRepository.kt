package com.example.comictracker.domain.repository

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel

interface RemoteComicRepository {

    suspend fun getSeriesLastReleasesById(id: Int): List<ComicModel>

    suspend fun getCharacterSeries(characterId: Int,offset:Int = 0): List<SeriesModel>

    suspend fun getAllSeries(loadedCount: Int = 0):List<SeriesModel>

    suspend fun getAllCharacters(loadedCount: Int = 0) :List<CharacterModel>

    suspend fun getSeriesByTitle(title:String):List<SeriesModel>

    suspend fun getCharactersByName(name: String): List<CharacterModel>

    suspend fun getSeriesById(id:Int):SeriesModel

    suspend fun getSeriesCreators(creatorsRoles: List<Pair<Int, String>>):List<CreatorModel>
    suspend fun getSeriesCharacters(seriesId: Int):List<CharacterModel>
    suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): List<SeriesModel>

    suspend fun getComicsFromSeries(seriesId: Int,loadedCount: Int = 0): List<ComicModel>

    suspend fun getComicById(comicId:Int): ComicModel

    suspend fun getCharacterById(characterId: Int): CharacterModel

    suspend fun  getComicCreators(creatorsRoles: List<Pair<Int, String>>):List<CreatorModel>

    suspend fun getComicCharacters(comicId: Int):List<CharacterModel>
}