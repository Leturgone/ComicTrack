package com.example.comictracker.data.repository

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.seriesDTO.Results as seriesResult
import com.example.comictracker.data.api.dto.comicsDTO.Results as comicsResult
import com.example.comictracker.data.api.dto.creatorsDTO.Results as creatorsResult
import com.example.comictracker.data.api.dto.charactersDTO.Results as charactersResult
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.RemoteComicRepository
import javax.inject.Inject

class RemoteComicRepositoryImpl @Inject constructor(private val api: MarvelComicApi) :RemoteComicRepository {


    //Мапперы
    private fun seriesResult.toModel():SeriesModel{
        return SeriesModel(
            seriesId = this.id!!.toInt(),
            title = this.title!!,
            date = "${this.startYear} - ${this.endYear}",
            desc = this.description!!,
            image = "${this.urls[0].url}${this.urls[0].type}",
            comics = this.comics!!.items.map {
                it.resourceURI!!.toInt()
            },
            creators = this.creators!!.items.map {
                it.resourceURI!!.toInt()
            },
            characters = this.characters!!.items.map {
                it.resourceURI!!.toInt()
            },
            connectedSeries = listOf(
                this.next?.resourceURI?.toInt(),
                this.previous?.resourceURI?.toInt()),
            readMark = ""
        )
    }

    private fun comicsResult.toModel():ComicModel{
        return ComicModel(
            comicId = this.id.toInt(),
            title =  this.title,
            number =  this.issueNumber,
            image = "${this.urls[0].url}${this.urls[0].type}",
            seriesId =this.series!!.resourceURI!!.toInt(),
            seriesTitle = this.series!!.name!!,
            date = this.dates[0].date.toString(),
            creators = this.creators!!.items.map {
                it.resourceURI!!.toInt()
            },
            characters = this.characters!!.items.map {
                it.resourceURI!!.toInt()
            },
            readMark = ""
        )
    }

    private fun creatorsResult.toModel():CreatorModel{
        return CreatorModel(
            creatorId = this.id!!.toInt(),
            name = this.fullName!!,
            image = "${this.urls[0].url}${this.urls[0].type}",
            role = this.suffix!!
        )
    }

    private fun charactersResult.toModel():CharacterModel{
        return CharacterModel(
            characterId = this.id.toInt(),
            name = this.name,
            image = "${this.urls[0].url}${this.urls[0].type}",
            desc = this.description,
            series = this.series.items.map {
                it.resourceURI!!.toInt()
            }
        )
    }



    override suspend fun getSeriesLastReleasesById(id: Int): List<ComicModel> {
        val seriesLastReleases = mutableListOf<ComicModel>()
        api.getSeriesLastReleasesById(series = id.toString()).data?.results?.forEach {
            results ->  seriesLastReleases.add(results.toModel())
        }
        return seriesLastReleases
    }

    override suspend fun getCharacterSeries(characterId: Int): List<SeriesModel> {
        val characterSeries = mutableListOf<SeriesModel>()
        api.getCharacterSeries(characterId).data?.results?.forEach { result ->
            characterSeries.add(
                result.toModel()
            )
        }
        return characterSeries
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