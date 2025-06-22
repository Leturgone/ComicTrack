package com.example.comictracker.domain.usecase.searchUseCases

import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository

class LoadSeriesSearchResultListUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository
) {

    suspend operator fun invoke(query: String) = remoteSeriesRepository.getSeriesByTitle(query)
}