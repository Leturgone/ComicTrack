package com.example.comictracker.domain.usecase.libraryUseCases

data class LibraryUseCases(
    val loadCurrentlyReadingSeriesUseCase: LoadCurrentlyReadingSeriesUseCase,
    val loadFavoriteSeriesUseCase: LoadFavoriteSeriesUseCase,
    val loadHistoryReadComicUseCase: LoadHistoryReadComicUseCase,
    val loadStatisticsUseCase: LoadStatisticsUseCase
)
