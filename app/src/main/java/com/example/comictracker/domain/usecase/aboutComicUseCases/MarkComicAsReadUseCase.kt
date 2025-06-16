package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class MarkComicAsReadUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localWriteRepository: LocalWriteRepository
) {

    suspend operator fun invoke(comicApiId: Int, seriesApiId: Int,number:String): Result<Unit>{
        val nextComicIdResult = remoteComicsRepository.getNextComicId(seriesApiId,number.toFloat().toInt())
        return nextComicIdResult.fold(
            onSuccess  = {nextComicId -> localWriteRepository.markComicRead(comicApiId,seriesApiId,nextComicId)},
            onFailure = {Result.failure(it)}
        )
    }
}