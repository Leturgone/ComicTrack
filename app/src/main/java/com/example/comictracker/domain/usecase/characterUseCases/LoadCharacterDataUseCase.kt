package com.example.comictracker.domain.usecase.characterUseCases

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import kotlinx.coroutines.async

class LoadCharacterDataUseCase(
    private val characterRepository: RemoteCharacterRepository
) {
    suspend operator fun invoke(characterId:Int): Result<CharacterModel> {
        return characterRepository.getCharacterById(characterId)
    }
}