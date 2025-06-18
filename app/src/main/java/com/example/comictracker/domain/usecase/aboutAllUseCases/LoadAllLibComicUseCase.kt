package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadAllLibComicUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(loadedCount:Int): Result<List<ComicModel>>{
        return localReadRepository.loadAllReadComicIds(loadedCount).fold(
            onSuccess = {allComicsFromBD ->
                remoteComicsRepository.fetchComics(allComicsFromBD)
            },
            onFailure = { Result.failure(it)}
        )
    }
}