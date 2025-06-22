package com.example.comictracker.domain.usecase.libraryUseCases

import com.example.comictracker.domain.repository.local.LocalReadRepository

class LoadStatisticsUseCase(
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke()= localReadRepository.loadStatistics()
}