package com.example.comictracker.domain.usecase.searchUseCase

import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadNewSeriesListUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository
){
    suspend operator fun invoke() = remoteSeriesRepository.getAllSeries()
}