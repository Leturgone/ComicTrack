package com.example.comictracker.domain.usecase.aboutSeriesUseCase

import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository

class LoadSeriesCharactersUseCase(
    private val remoteCharacterRepository: RemoteCharacterRepository
) {
    suspend operator fun invoke(seriesId: Int) = remoteCharacterRepository.getSeriesCharacters(seriesId)
}