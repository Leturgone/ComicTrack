package com.example.comictracker.domain.usecase.searchUseCase

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadMayLikeSeriesListUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(): Result<List<SeriesModel>>{
        val loadedIdsSeriesFromBD  = localReadRepository.loadAllReadSeriesIds(0)
        return loadedIdsSeriesFromBD.fold(
            onSuccess = {loadedIds ->
                remoteSeriesRepository.loadMayLikeSeriesIds(loadedIds).fold(
                    onSuccess = { idList ->
                        remoteSeriesRepository.fetchSeries(idList)
                    },
                    onFailure = { Result.failure(it)}
                )
            },
            onFailure = {Result.failure(it)}
        )

    }
}