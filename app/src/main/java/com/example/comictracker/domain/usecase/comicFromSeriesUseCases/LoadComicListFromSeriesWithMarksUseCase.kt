package com.example.comictracker.domain.usecase.comicFromSeriesUseCases

import android.util.Log
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


//use case для загрузки списка комиксов в конкретной серии
class LoadComicListFromSeriesWithMarksUseCase(
    private val remoteComicsRepository: RemoteComicsRepository,
    private val localReadRepository: LocalReadRepository
){
    suspend operator fun invoke(seriesId: Int,loadedCount: Int) = withContext(Dispatchers.IO){
        remoteComicsRepository.getComicsFromSeries(seriesId,loadedCount).fold(
            onSuccess = {
                val comicsWithReadMarks = it.map { comic ->
                    async {
                        localReadRepository.loadComicMark(comic.comicId).fold(
                            onSuccess =  {readMark ->
                                Log.i("ReadMarK", "${comic.number} $readMark")
                                comic.copy(readMark = readMark)
                            },
                            onFailure = {
                                Log.e("ReadMarK", "${comic.number} ")
                                comic
                            }
                        )
                    }
                }.awaitAll()
                Result.success(comicsWithReadMarks)
            },
            onFailure = { Result.failure(it)}
        )
    }
}