package com.example.comictracker.domain.usecase.searchUseCases

import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository

class LoadCharactersSearchResultListUseCase(
    private val remoteCharacterRepository: RemoteCharacterRepository
) {

    suspend operator fun invoke(query: String) = remoteCharacterRepository.getCharactersByName(query)
}