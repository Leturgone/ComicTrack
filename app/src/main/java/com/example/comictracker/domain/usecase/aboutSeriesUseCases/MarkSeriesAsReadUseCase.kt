package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository

class MarkSeriesAsReadUseCase(
    private val localWriteRepository: LocalWriteRepository
) {

    suspend operator fun invoke(apiId:Int) =  localWriteRepository.markSeriesRead(apiId)
}