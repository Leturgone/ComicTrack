package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository

class MarkSeriesAsWillBeReadUseCase(
    private val localWriteRepository: LocalWriteRepository
) {

    suspend operator fun invoke(apiId:Int) = localWriteRepository.addSeriesToWillBeRead(apiId)
}