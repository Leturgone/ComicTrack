package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadAllCurrentReadingSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(loadedCount:Int): Result<List<SeriesModel>> {
        return localReadRepository.loadCurrentReadIds(loadedCount).fold(
            onSuccess = {currentSeriesFromBD ->
                remoteSeriesRepository.fetchSeries(currentSeriesFromBD)
            },
            onFailure = { Result.failure(it)}
        )
    }
}