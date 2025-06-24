package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository

class AddSeriesToFavoritesUseCase(
    private val localWriteRepository: LocalWriteRepository
) {

    suspend operator fun invoke(apiId: Int) =  localWriteRepository.addSeriesToFavorite(apiId)
}