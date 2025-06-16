package com.example.comictracker.domain.usecase.aboutSeriesUseCase

import com.example.comictracker.domain.repository.remote.RemoteComicsRepository

class LoadComicsListUseCase(
    private val remoteComicsRepository: RemoteComicsRepository
) {
    suspend operator fun invoke(seriesId: Int) = remoteComicsRepository.getComicsFromSeries(seriesId)
}