package com.example.comictracker.domain.usecase.aboutAllUseCases

import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.presentation.mvi.DataState

class LoadAllReadListUseCase(
    private val remoteSeriesRepository: RemoteSeriesRepository,
    private val localReadRepository: LocalReadRepository
) {
    suspend operator fun invoke(loadedCount:Int){
        return localReadRepository.loadWillBeReadIds(loadedCount).fold(
            onSuccess = {readlistFromBD->
                remoteSeriesRepository.fetchSeries(readlistFromBD)
            },
            onFailure = { DataState.Error("Error while loading all readlist")}
        )
    }
}