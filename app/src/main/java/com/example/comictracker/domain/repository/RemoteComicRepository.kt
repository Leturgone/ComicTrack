package com.example.comictracker.domain.repository

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel

interface RemoteComicRepository {

    suspend fun getSeriesLastReleasesById(id: Int): List<SeriesModel>

    suspend fun getCharacterSeries(characterId: Int): List<SeriesModel>

    suspend fun getAllSeries():List<SeriesModel>

    suspend fun getAllCharacters():List<CharacterModel>

    suspend fun getSeriesByTitle(title:String):List<SeriesModel>

    suspend fun getSeriesById(id:String):SeriesModel

    suspend fun getSeriesCreators(seriesId:Int):List<CreatorModel>

    suspend fun getComicsFromSeries(seriesId: Int): List<ComicModel>

    suspend fun getComicById(comicId:Int): ComicModel

    suspend fun  getComicCreators(comicId: Int):List<CreatorModel>

    suspend fun getComicCharacters(comicId: Int):List<CharacterModel>
}