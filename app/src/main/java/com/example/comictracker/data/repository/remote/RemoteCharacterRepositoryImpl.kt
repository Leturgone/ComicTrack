package com.example.comictracker.data.repository.remote

import android.util.Log
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.charactersDTO.Results
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import javax.inject.Inject

class RemoteCharacterRepositoryImpl @Inject constructor(private val api: MarvelComicApi):RemoteCharacterRepository {

    //Маппер
    private fun Results.toModel():CharacterModel{
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

    override suspend fun getAllCharacters(loadedCount: Int): List<CharacterModel> {
        val allCharacters = mutableListOf<CharacterModel>()
        api.getAllCharacters(offset = loadedCount.toString()).data?.results?.forEach {
                result -> allCharacters.add(result.toModel())
        }
        return  allCharacters
    }

    override suspend fun getCharactersByName(name: String): List<CharacterModel> {
        val characters = mutableListOf<CharacterModel>()
        api.getCharactersByName(name).data?.results?.forEach {
                result -> characters.add(result.toModel())
        }
        return characters
    }

    override suspend fun getSeriesCharacters(seriesId: Int): List<CharacterModel> {
        val characters = mutableListOf<CharacterModel>()
        Log.i("getSeriesCharacters","Start get sereies characters")
        api.getSeriesCharacters(seriesId.toString()).data!!
            .results.forEach {result ->
                characters.add(result.toModel())
            }
        Log.i("getSeriesCharacters","got sereies characters $characters")
        return characters
    }

    override suspend fun getCharacterById(characterId: Int): CharacterModel {
        Log.i("getCharacterById","Start get character ")
        val result = api.getCharacterById(characterId.toString()).data!!.results[0]
        Log.i("getCharacterById","got character ")
        val convertedRes = result.toModel()
        Log.i("getCharacterById","Converted $convertedRes ")
        return convertedRes
    }

    override suspend fun getComicCharacters(comicId: Int): List<CharacterModel> {
        val characters = mutableListOf<CharacterModel>()
        api.getComicCharacters(comicId.toString()).data!!.results.forEach {result ->
            characters.add(result.toModel())
        }
        return characters
    }
}