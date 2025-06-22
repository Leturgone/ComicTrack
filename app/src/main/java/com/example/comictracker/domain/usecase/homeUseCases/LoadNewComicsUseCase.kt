package com.example.comictracker.domain.usecase.homeUseCases

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadNewComicsUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(): Result<List<ComicModel>>{
        val loadedIdsSeriesFromBDDef = localReadRepository.loadCurrentReadIds(0)
        return loadedIdsSeriesFromBDDef.fold(
            onSuccess = {
                remoteComicsRepository.fetchUpdatesForSeries(it)
            },
            onFailure = {Result.failure(it)}
        )
    }
}