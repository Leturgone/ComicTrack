package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository

class AddSeriesToFavoritesUseCase(
    private val localWriteRepository: LocalWriteRepository,
    private val localReadRepository: LocalReadRepository
) {

    suspend operator fun invoke(apiId: Int): Result<Unit>{
        return localReadRepository.loadFavoritesCount().fold(
            onSuccess = { favoritesCount ->
                if (favoritesCount<4){
                    localWriteRepository.addSeriesToFavorite(apiId)
                }else{
                    Result.failure(Exception())
                }},
            onFailure = {
                Result.failure(it)
            }
        )
    }
}