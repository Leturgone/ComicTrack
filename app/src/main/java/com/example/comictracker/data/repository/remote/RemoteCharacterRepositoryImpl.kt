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

    override suspend fun getAllCharacters(loadedCount: Int): Result<List<CharacterModel>> {
        return  try {
            val allCharacters = mutableListOf<CharacterModel>()
            api.getAllCharacters(offset = loadedCount.toString()).data?.results?.forEach {
                    result -> allCharacters.add(result.toModel())
            }
            Result.success(allCharacters)
        }catch (e:Exception){
            Log.e("getAllCharacters", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getCharactersByName(name: String): Result<List<CharacterModel>> {
        return try {
            val characters = mutableListOf<CharacterModel>()
            api.getCharactersByName(name).data?.results?.forEach {
                    result -> characters.add(result.toModel())
            }
            Result.success(characters)
        }catch (e:Exception){
            Log.e("getCharactersByName", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getSeriesCharacters(seriesId: Int): Result<List<CharacterModel>> {
        return try {
            val characters = mutableListOf<CharacterModel>()
            api.getSeriesCharacters(seriesId.toString()).data!!
                .results.forEach {result ->
                    characters.add(result.toModel())
                }
            Log.i("getSeriesCharacters","got series characters $characters")
            Result.success(characters)
        }catch (e:Exception){
            Log.e("getSeriesCharacters", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getCharacterById(characterId: Int): Result<CharacterModel> {
        return try {
            Log.i("getCharacterById","Start get character ")
            val result = api.getCharacterById(characterId.toString()).data!!.results[0]
            Log.i("getCharacterById","got character ")
            val convertedRes = result.toModel()
            Log.i("getCharacterById","Converted $convertedRes ")
            Result.success(convertedRes)
        }catch (e:Exception){
            Log.e("getCharacterById", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getComicCharacters(comicId: Int): Result<List<CharacterModel>> {
        return try {
            val characters = mutableListOf<CharacterModel>()
            api.getComicCharacters(comicId.toString()).data!!.results.forEach {result ->
                characters.add(result.toModel())
            }
            Result.success(characters)
        }catch (e:Exception){
            Log.e("getComicCharacters", e.toString())
            Result.failure(e)
        }
    }
}