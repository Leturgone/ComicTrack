package com.example.comictracker.domain.usecase.searchUseCase

import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import kotlinx.coroutines.async

class LoadNewSeriesListUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository
){
    suspend operator fun invoke() = remoteSeriesRepository.getAllSeries()
}