package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadAllNewComicUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(loadedCount:Int): Result<List<ComicModel>> {
        return localReadRepository.loadCurrentReadIds(loadedCount).fold(
            onSuccess = {loadedIdsSeriesFromBD ->
                remoteComicsRepository.fetchUpdatesForSeries(loadedIdsSeriesFromBD)
            },
            onFailure = { Result.failure(it) }
        )
    }
}