package com.example.comictracker.domain.usecase.aboutSeriesUseCase

import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoadSeriesDataUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(seriesId:Int) = coroutineScope{
        val seriesDeferred = async{
            remoteSeriesRepository.getSeriesById(seriesId)
        }
        val series = seriesDeferred.await().fold(
            onSuccess = {it},
            onFailure = {
                return@coroutineScope Result.failure(it)}
        ) // Получение series до зависимых задач.

        val readMarkDef = async { localReadRepository.loadSeriesMark(series.seriesId)}
        val favoriteMarkDef = async { localReadRepository.loadSeriesFavoriteMark(series.seriesId) }

        val readMark = readMarkDef.await().fold(
            onSuccess ={it},
            onFailure = {
                return@coroutineScope Result.failure(it)
            }
        )
        val favoriteMark = favoriteMarkDef.await().fold(
            onSuccess ={it},
            onFailure = {
                return@coroutineScope Result.failure(it)
            }
        )

        val seriesWithMark = series.copy(readMark = readMark, favoriteMark = favoriteMark)
        Result.success(seriesWithMark)
    }
}