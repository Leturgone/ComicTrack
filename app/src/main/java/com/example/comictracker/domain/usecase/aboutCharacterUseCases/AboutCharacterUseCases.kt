package com.example.comictracker.domain.usecase.aboutCharacterUseCases

data class AboutCharacterUseCases(
    val loadCharacterDataUseCase: LoadCharacterDataUseCase,
    val loadSeriesWithCharacterUseCase: LoadSeriesWithCharacterUseCase
)