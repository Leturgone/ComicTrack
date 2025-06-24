package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadAllLastComicUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(loadedCount:Int): Result<List<ComicModel>>{
        return localReadRepository.loadHistory(loadedCount).fold(
            onSuccess = {lastComicsFromBD ->
                remoteComicsRepository.fetchComics(lastComicsFromBD)
            },
            onFailure = { Result.failure(it)}
        )
    }
}