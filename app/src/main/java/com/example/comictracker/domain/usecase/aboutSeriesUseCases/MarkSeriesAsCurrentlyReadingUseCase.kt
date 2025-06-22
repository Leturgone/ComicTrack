package com.example.comictracker.domain.usecase.aboutSeriesUseCases

import com.example.comictracker.domain.repository.local.LocalWriteRepository

class MarkSeriesAsCurrentlyReadingUseCase(
    private val localWriteRepository: LocalWriteRepository
) {
    suspend operator fun  invoke(apiId:Int,firstIssueId:Int?) = localWriteRepository.addSeriesToCurrentlyRead(apiId,firstIssueId)
}