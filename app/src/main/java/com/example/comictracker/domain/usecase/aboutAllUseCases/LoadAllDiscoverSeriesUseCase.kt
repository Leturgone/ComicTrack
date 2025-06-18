package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadAllDiscoverSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository
) {
    suspend operator fun invoke(loadedCount:Int) = remoteSeriesRepository.getAllSeries(loadedCount)
}