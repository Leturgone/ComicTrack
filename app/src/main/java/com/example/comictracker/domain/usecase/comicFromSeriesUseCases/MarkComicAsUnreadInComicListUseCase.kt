package com.example.comictracker.domain.usecase.comicFromSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

//use case для отметки не прочитано комикса в списке в конкретной серии
class MarkComicAsUnreadInComicListUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localWriteRepository: LocalWriteRepository
) {
    suspend operator fun invoke(comicApiId: Int, seriesApiId: Int, number: String): Result<Unit>{
        return remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt()).fold(
            onSuccess = {prevComicId ->
                localWriteRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)
            },
            onFailure = {Result.failure(it)}
        )
    }
}