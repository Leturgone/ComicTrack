package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository

class LoadComicCreatorsUseCase(
    private val remoteCreatorsRepository: RemoteCreatorsRepository
) {
    suspend operator fun invoke(creators: List<Pair<Int, String>>): Result<List<CreatorModel>> {
        return remoteCreatorsRepository.getComicCreators(creators)
    }
}