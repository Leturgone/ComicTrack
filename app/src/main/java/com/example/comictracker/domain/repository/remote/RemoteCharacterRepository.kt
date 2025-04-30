package com.example.comictracker.domain.repository.remote

import com.example.comictracker.domain.model.CharacterModel

interface RemoteCharacterRepository {

    suspend fun getAllCharacters(loadedCount: Int = 0) :List<CharacterModel>

    suspend fun getCharactersByName(name: String): List<CharacterModel>

    suspend fun getSeriesCharacters(seriesId: Int):List<CharacterModel>

    suspend fun getCharacterById(characterId: Int): CharacterModel

    suspend fun getComicCharacters(comicId: Int):List<CharacterModel>

}