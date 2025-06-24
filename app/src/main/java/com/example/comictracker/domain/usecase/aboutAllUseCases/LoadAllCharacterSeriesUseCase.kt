package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadAllCharacterSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository
){
    suspend operator fun invoke(characterId: Int,loadedCount:Int) = remoteSeriesRepository.getCharacterSeries(characterId,loadedCount)
}