package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository

class LoadSeriesCreatorsUseCase(
    private val remoteCreatorsRepository: RemoteCreatorsRepository
) {
    suspend operator fun invoke(series: SeriesModel) = remoteCreatorsRepository.getSeriesCreators(series.creators?: emptyList())
}