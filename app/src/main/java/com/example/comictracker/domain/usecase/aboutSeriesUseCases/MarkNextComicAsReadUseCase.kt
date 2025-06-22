package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class MarkNextComicAsReadUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localWriteRepository: LocalWriteRepository
) {
    suspend operator fun invoke(comicApiId: Int, seriesApiId: Int, number: String): Result<Unit> {
        return remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt()).fold(
            onSuccess = {nextComicId ->
                localWriteRepository.markComicRead(comicApiId,seriesApiId,nextComicId)
            },
            onFailure = {Result.failure(it)}
        )
    }
}