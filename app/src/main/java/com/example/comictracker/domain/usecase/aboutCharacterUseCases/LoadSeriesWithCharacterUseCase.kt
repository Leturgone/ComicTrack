package com.example.comictracker.domain.usecase.aboutCharacterUseCases

import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadSeriesWithCharacterUseCase(
    private val seriesRepository: RemoteSeriesRepository
) {

    suspend operator fun invoke(characterId:Int) = seriesRepository.getCharacterSeries(characterId)
}