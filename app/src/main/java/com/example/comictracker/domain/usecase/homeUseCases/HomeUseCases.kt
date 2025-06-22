package com.example.comictracker.domain.usecase.homeUseCases

data class HomeUseCases(
    val loadCurrentNextComicUseCase: LoadCurrentNextComicsUseCase,
    val loadNewComicsUseCase: LoadNewComicsUseCase
)
