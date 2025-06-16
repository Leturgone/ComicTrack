package com.example.comictracker.domain.usecase.characterUseCases

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadSeriesWithCharacterUseCase(
    private val seriesRepository: RemoteSeriesRepository
) {
    suspend operator fun invoke(characterId:Int): Result<List<SeriesModel>> {
        return seriesRepository.getCharacterSeries(characterId)
    }
}