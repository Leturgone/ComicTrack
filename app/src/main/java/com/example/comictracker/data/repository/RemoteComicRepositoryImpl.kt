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
            image = "${this.thumbnail?.path}.${this.thumbnail?.path}",
            comics = this.comics!!.items.mapNotNull {
                it.resourceURI?.substringAfter("comics/")?.toIntOrNull()
            },
            creators = this.creators!!.items.map {
                Pair(
                    it.resourceURI!!.substringAfter("creators/").toInt(),
                    it.role!!)
            },
            characters = this.characters!!.items.mapNotNull {
                it.resourceURI?.substringAfter("characters/")?.toIntOrNull()
            },
            connectedSeries = listOf(
                this.next?.resourceURI?.substringAfter("series/")?.toIntOrNull(),
                this.previous?.resourceURI?.substringAfter("series/")?.toIntOrNull(),),
            readMark = ""
        )
    }

    private fun comicsResult.toModel():ComicModel{
        return ComicModel(
            comicId = this.id.toInt(),
            title =  this.title,
            number =  this.issueNumber,
            image = "${this.images[0].path}${this.images[0].extension}",
            seriesId = this.series!!.resourceURI!!.substringAfter("series/").toInt(),
            seriesTitle = this.series!!.name!!,
            date = this.dates[0].date.toString(),
            creators = this.creators!!.items.map {
                Pair(
                    it.resourceURI!!.substringAfter("creators/").toInt(),
                    it.role!!)
            },
            characters = this.characters!!.items.mapNotNull {
                it.resourceURI?.substringAfter("characters/")?.toIntOrNull()
            },
            readMark = ""
        )
    }

    private fun creatorsResult.toModel(role:String):CreatorModel{
        return CreatorModel(
            creatorId = this.id!!.toInt(),
            name = this.fullName!!,
            image = "${this.thumbnail?.path}${this.thumbnail?.extension}",
            role = role
        )
    }

    private fun charactersResult.toModel():CharacterModel{
        return CharacterModel(
            characterId = this.id.toInt(),
            name = this.name,
            image = "${this.thumbnail.path}${this.thumbnail.extension}",
            desc = this.description,
            series = this.series.items.mapNotNull {
                it.resourceURI?.substringAfter("series/")?.toIntOrNull()
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
        api.getCharacterSeries(characterId).data?.results?.forEach {
            result -> characterSeries.add(result.toModel())
        }
        return characterSeries
    }

    override suspend fun getAllSeries(): List<SeriesModel> {
        val allSeries = mutableListOf<SeriesModel>()
        api.getAllSeries().data?.results?.forEach {
            result -> allSeries.add(result.toModel())
        }
        return allSeries
    }

    override suspend fun getAllCharacters(): List<CharacterModel> {
        val allCharacters = mutableListOf<CharacterModel>()
        api.getAllCharacters().data?.results?.forEach {
            result -> allCharacters.add(result.toModel())
        }
        return  allCharacters
    }

    override suspend fun getSeriesByTitle(title: String): List<SeriesModel> {
        val series = mutableListOf<SeriesModel>()
        api.getSeriesByTitle(title).data?.results?.forEach {
            results ->   series.add(results.toModel())
        }
        return series
    }

    override suspend fun getSeriesById(id: String): SeriesModel {
        return api.getSeriesById(id).data!!.results[0].toModel()
    }

    override suspend fun getSeriesCreators(seriesId: Int): List<CreatorModel> {
        val creators = mutableListOf<CreatorModel>()
        api.getSeriesById(seriesId.toString()).data!!.results[0]
            .toModel().creators.forEach { creator ->
                creators.add(api.getCreatorById(creator.first.toString())
                    .data!!.results[0].toModel(creator.second))
            }
        return creators
    }

    override suspend fun getComicsFromSeries(seriesId: Int): List<ComicModel> {
        val comics = mutableListOf<ComicModel>()
        api.getComicsFromSeries(seriesId.toString()).data!!.results.forEach {
            result ->  comics.add(result.toModel())
        }
        return comics
    }

    override suspend fun getComicById(comicId: Int): ComicModel {
        return api.getComicById(comicId.toString()).data!!.results[0].toModel()
    }

    override suspend fun getComicCreators(comicId: Int): List<CreatorModel> {
        val creators = mutableListOf<CreatorModel>()
        api.getComicById(comicId.toString()).data!!.results[0]
            .toModel().creators.forEach { creator ->
                creators.add(api.getCreatorById(creator.first.toString())
                    .data!!.results[0].toModel(creator.second))
            }
        return creators
    }

    override suspend fun getComicCharacters(comicId: Int): List<CharacterModel> {
        val characters = mutableListOf<CharacterModel>()
        api.getComicCharacters(comicId.toString()).data!!.results.forEach {result ->
            characters.add(result.toModel())
        }
        return characters
    }

}