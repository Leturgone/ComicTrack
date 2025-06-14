package com.example.comictracker.domain.repository.remote

import com.example.comictracker.domain.model.CharacterModel

interface RemoteCharacterRepository {

    suspend fun getAllCharacters(loadedCount: Int = 0): Result<List<CharacterModel>>

    suspend fun getCharactersByName(name: String): Result<List<CharacterModel>>

    suspend fun getSeriesCharacters(seriesId: Int): Result<List<CharacterModel>>

    suspend fun getCharacterById(characterId: Int): Result<CharacterModel>

    suspend fun getComicCharacters(comicId: Int): Result<List<CharacterModel>>

}