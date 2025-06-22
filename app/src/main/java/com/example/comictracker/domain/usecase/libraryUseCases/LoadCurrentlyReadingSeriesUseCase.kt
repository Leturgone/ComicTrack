package com.example.comictracker.domain.usecase.libraryUseCases

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadCurrentlyReadingSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {

    suspend operator fun invoke(): Result<List<SeriesModel>>{
        val loadedCurrentlyReadingSeriesIdsFromBD = localReadRepository.loadCurrentReadIds(0)
        return loadedCurrentlyReadingSeriesIdsFromBD.fold(
            onSuccess = {idList ->
                remoteSeriesRepository.fetchSeries(idList)
            },
            onFailure = {Result.failure(it)}
        )
    }
}