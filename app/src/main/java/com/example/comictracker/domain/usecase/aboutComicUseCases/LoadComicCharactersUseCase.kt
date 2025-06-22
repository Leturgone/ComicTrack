package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository

class LoadComicCharactersUseCase(
    private val remoteCharacterRepository: RemoteCharacterRepository
) {

    suspend operator fun invoke(comicId:Int) = remoteCharacterRepository.getComicCharacters(comicId)
}