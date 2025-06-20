package com.example.comictracker.domain.usecase.comicFromSeriesUseCase

import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

//use case для отметки прочитано комикса в списке в конкретной серии
class MarkComicAsReadInComicListUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localWriteRepository: LocalWriteRepository
){
    suspend operator fun invoke(comicApiId: Int, seriesApiId: Int, number: String): Result<Unit> {
        return remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt()).fold(
            onSuccess = {nextComicId ->
                localWriteRepository.markComicRead(comicApiId,seriesApiId,nextComicId)
            },
            onFailure = {Result.failure(it)}
        )
    }
}