package com.example.comictracker.domain.usecase.aboutSeriesUseCases

data class AboutSeriesUseCases(
    val loadComicsListUseCase: LoadComicsListUseCase,
    val loadConnectedSeriesUseCase: LoadConnectedSeriesUseCase,
    val loadNextComicInSeriesUseCase: LoadNextComicInSeriesUseCase,
    val loadSeriesCharactersUseCase: LoadSeriesCharactersUseCase,
    val loadSeriesCreatorsUseCase: LoadSeriesCreatorsUseCase,
    val loadSeriesDataUseCase: LoadSeriesDataUseCase
)
