package com.example.comictracker.domain.usecase.aboutSeriesUseCase

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoadNextComicInSeriesUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(comicList:List<ComicModel>, seriesId:Int) = coroutineScope {
        val nextReadLocDef = async {  localReadRepository.loadNextRead(seriesId)}

        val nextReadDef = async {
            val nextReadComicId = nextReadLocDef.await().fold(
                onSuccess = {it},
                onFailure = {null}
            )
            nextReadComicId?.let { nextComicId ->
                remoteComicsRepository.getComicById(nextComicId).fold(
                    onSuccess = {it},
                    onFailure = {null}
                )
            }
        }

        nextReadDef.await()?: if (comicList.isNotEmpty()) comicList[0] else null
    }
}