package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository

class LoadComicCreatorsUseCase(
    private val remoteCreatorsRepository: RemoteCreatorsRepository
) {
    suspend operator fun invoke(creators: List<Pair<Int, String>>) = remoteCreatorsRepository.getComicCreators(creators)
}