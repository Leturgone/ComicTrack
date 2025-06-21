package com.example.comictracker.domain.usecase.libraryUseCase

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadHistoryReadComicUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(): Result<List<ComicModel>>{
        val loadedHistoryReadComicFromBD = localReadRepository.loadHistory(0)
        return loadedHistoryReadComicFromBD.fold(
            onSuccess = {idList ->
                remoteComicsRepository.fetchComics(idList)
            },
            onFailure = {Result.failure(it)}
        )
    }
}