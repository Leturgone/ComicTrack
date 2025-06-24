package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadConnectedSeriesUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository
) {

    suspend operator fun invoke(series:SeriesModel) = remoteSeriesRepository.getConnectedSeries(series.connectedSeries)
}