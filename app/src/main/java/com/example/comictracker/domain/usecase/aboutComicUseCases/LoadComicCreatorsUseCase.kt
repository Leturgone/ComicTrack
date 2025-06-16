package com.example.comictracker.domain.usecase.aboutComicUseCases

import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository

class LoadComicCreatorsUseCase(
    private val remoteCreatorsRepository: RemoteCreatorsRepository
) {
    suspend operator fun invoke(comic:ComicModel) = remoteCreatorsRepository.getComicCreators(comic.creators)
}