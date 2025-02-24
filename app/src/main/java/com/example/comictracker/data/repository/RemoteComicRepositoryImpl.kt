package com.example.comictracker.data.repository

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.RemoteComicRepository
import javax.inject.Inject

class RemoteComicRepositoryImpl @Inject constructor(private val api: MarvelComicApi) :RemoteComicRepository {
    override suspend fun getSeriesLastReleasesById(id: Int): List<ComicModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterSeries(characterId: Int): List<SeriesModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSeries(): List<SeriesModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCharacters(): List<CharacterModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesByTitle(title: String): List<SeriesModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesById(id: String): SeriesModel {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesCreators(seriesId: Int): List<CreatorModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getComicsFromSeries(seriesId: Int): List<ComicModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getComicById(comicId: Int): ComicModel {
        TODO("Not yet implemented")
    }

    override suspend fun getComicCreators(comicId: Int): List<CreatorModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getComicCharacters(comicId: Int): List<CharacterModel> {
        TODO("Not yet implemented")
    }

}