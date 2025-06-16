package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class MarkComicUnreadUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localWriteRepository: LocalWriteRepository
) {

    suspend operator fun invoke(comicApiId: Int, seriesApiId: Int,number:String): Result<Unit>{
        val prevComicIdResult = remoteComicsRepository.getPreviousComicId(seriesApiId, number.toFloat().toInt())
        return prevComicIdResult.fold(
            onSuccess  = {prevComicId -> localWriteRepository.markComicUnread(comicApiId,seriesApiId,prevComicId)},
            onFailure = {Result.failure(it)}
        )
    }
}