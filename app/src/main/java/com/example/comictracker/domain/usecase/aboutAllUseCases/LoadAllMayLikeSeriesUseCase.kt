package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadAllMayLikeSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(loadedCount:Int): Result<List<SeriesModel>>{
        return localReadRepository.loadAllReadSeriesIds(loadedCount).fold(
            onSuccess = {loadedIdsSeriesFromBD ->
                remoteSeriesRepository.loadMayLikeSeriesIds(loadedIdsSeriesFromBD).fold(
                    onSuccess = { ids ->
                        remoteSeriesRepository.fetchSeries(ids)
                    },
                    onFailure = { Result.failure(it)}
                )
            },
            onFailure = { Result.failure(it)}
        )
    }
}