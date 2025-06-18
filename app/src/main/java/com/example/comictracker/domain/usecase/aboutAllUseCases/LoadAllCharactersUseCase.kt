package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository

class LoadAllCharactersUseCase(
    private val remoteCharacterRepository: RemoteCharacterRepository
) {
    suspend operator fun invoke(loadedCount: Int)  = remoteCharacterRepository.getAllCharacters(loadedCount)
}