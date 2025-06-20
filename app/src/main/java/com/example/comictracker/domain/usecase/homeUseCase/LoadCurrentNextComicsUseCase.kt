package com.example.comictracker.domain.usecase.homeUseCase

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadCurrentNextComicsUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(): Result<List<ComicModel>>{
        val loadedIdsNextReadComicFromBD = localReadRepository.loadNextReadComicIds(0)
        return loadedIdsNextReadComicFromBD.fold(
            onSuccess = {
                remoteComicsRepository.fetchComics(it)
            },
            onFailure = {Result.failure(it)}
        )
    }
}