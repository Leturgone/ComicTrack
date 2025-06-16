package com.example.comictracker.domain.usecase.aboutCharacterUseCases

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository

class LoadCharacterDataUseCase(
    private val characterRepository: RemoteCharacterRepository
) {
    suspend operator fun invoke(characterId:Int): Result<CharacterModel> {
        return characterRepository.getCharacterById(characterId)
    }
}