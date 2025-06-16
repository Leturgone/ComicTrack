package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadComicDataUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(comicId: Int):Result<ComicModel>{
        val  comicResult = remoteComicsRepository.getComicById(comicId)
        return comicResult.fold(
            onSuccess = {comic ->
                val markResult = localReadRepository.loadComicMark(comic.comicId)
                markResult.fold(
                    onSuccess = {Result.success(comic.copy(readMark = it))},
                    onFailure = {Result.failure(it)}
                )
            },
            onFailure = {Result.failure(it)}
        )
    }
}