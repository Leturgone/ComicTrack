package com.example.comictracker.data.repository

import android.util.Log
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
            desc = this.description,
            image = "${this.thumbnail?.path}.${this.thumbnail?.extension}",
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
            image = "${this.images[0].path}.${this.images[0].extension}",
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
            image = "${this.thumbnail?.path}.${this.thumbnail?.extension}",
            role = role
        )
    }

    private fun charactersResult.toModel():CharacterModel{
        return CharacterModel(
            characterId = this.id.toInt(),
            name = this.name,
            image = "${this.thumbnail.path}.${this.thumbnail.extension}",
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
        Log.i("Repository","Start get Character sereies")

        val res = api.getCharacterSeries(characterId.toString()).data!!.results
        res.forEach {
            result -> characterSeries.add(result.toModel())
        }
        Log.i("Repository","gоt Character sereies: $characterSeries")
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
        Log.i("Repository","Start get sereies")
        val result = api.getSeriesById(id).data!!.results[0]
        Log.i("Repository","Series got")
        val convertesRes = result.toModel()
        Log.i("Repository","Coverted $convertesRes")
        return convertesRes
    }

    //Два вызова
    override suspend fun getSeriesCreators(creatorsRoles: List<Pair<Int, String>>): List<CreatorModel> {
        val creators = mutableListOf<CreatorModel>()
        Log.i("Repository","Start get sereies creators")
        creatorsRoles.forEach { creator ->
                creators.add(api.getCreatorById(creator.first.toString())
                    .data!!.results[0].toModel(creator.second))
            }
        Log.i("Repository","creators got $creators")
        return creators
    }

    override suspend fun getSeriesCharacters(seriesId: Int): List<CharacterModel> {
        val characters = mutableListOf<CharacterModel>()
        Log.i("Repository","Start get sereies characters")
        api.getSeriesCharacters(seriesId.toString()).data!!
            .results.forEach {result ->
            characters.add(result.toModel())
        }
        Log.i("Repository","got sereies characters $characters")
        return characters
    }

    //Два вызова
    override suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): List<SeriesModel> {
        val series = mutableListOf<SeriesModel>()
        Log.i("Repository","Start get connected sereies ")
        connectedSeriesId.filterNotNull().forEach {
            series.add(api.getSeriesById(it.toString()).data!!.results[0].toModel())
        }
        Log.i("Repository","Start get connected got ")
        return series
    }

    override suspend fun getComicsFromSeries(seriesId: Int): List<ComicModel> {
        val comics = mutableListOf<ComicModel>()
        api.getComicsFromSeries(seriesId.toString()).data!!.results.forEach {
            result ->  comics.add(result.toModel())
        }
        return comics
    }

    override suspend fun getComicById(comicId: Int): ComicModel {
        Log.i("Repository","Start get comic ")
        val result = api.getComicById(comicId.toString()).data!!.results[0]
        Log.i("Repository","got comic ")
        val convertedRes = result.toModel()
        Log.i("Repository","Converted $convertedRes ")
        return convertedRes
    }
    override suspend fun getCharacterById(characterId: Int): CharacterModel {
        Log.i("Repository","Start get character ")
        val result = api.getCharacterById(characterId.toString()).data!!.results[0]
        Log.i("Repository","got character ")
        val convertedRes = result.toModel()
        Log.i("Repository","Converted $convertedRes ")
        return convertedRes
    }

    //Два вызова
    override suspend fun getComicCreators(creatorsRoles: List<Pair<Int, String>>): List<CreatorModel> {
        val creators = mutableListOf<CreatorModel>()
        creatorsRoles.forEach { creator ->
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