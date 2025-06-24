package com.example.comictracker.domain.usecase.searchUseCases

data class SearchUseCases(
    val loadCharactersListUseCase: LoadCharactersListUseCase,
    val loadCharactersSearchResultListUseCase: LoadCharactersSearchResultListUseCase,
    val loadMayLikeSeriesListUseCase: LoadMayLikeSeriesListUseCase,
    val loadNewSeriesListUseCase: LoadNewSeriesListUseCase,
    val loadSeriesSearchResultListUseCase: LoadSeriesSearchResultListUseCase
)
