package com.example.comictracker.domain.usecase.libraryUseCase

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadFavoriteSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(): Result<List<SeriesModel>>{
        val loadedFavoriteSeriesIdsFromBD = localReadRepository.loadFavoritesIds()
        return loadedFavoriteSeriesIdsFromBD.fold(
            onSuccess = {idList ->
                remoteSeriesRepository.fetchSeries(idList)
            },
            onFailure = {Result.failure(it)}
        )
    }
}