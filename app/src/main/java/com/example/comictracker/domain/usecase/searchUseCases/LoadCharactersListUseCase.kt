package com.example.comictracker.domain.usecase.searchUseCases

import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository

class LoadCharactersListUseCase(
    private val remoteCharacterRepository: RemoteCharacterRepository
) {

    suspend operator fun invoke() = remoteCharacterRepository.getAllCharacters()
}