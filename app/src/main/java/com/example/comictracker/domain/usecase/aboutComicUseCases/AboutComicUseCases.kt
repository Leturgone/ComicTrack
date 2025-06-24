package com.example.comictracker.domain.usecase.aboutComicUseCases

data class AboutComicUseCases(
    val loadComicCharactersUseCase: LoadComicCharactersUseCase,
    val loadComicCreatorsUseCase: LoadComicCreatorsUseCase,
    val loadComicDataUseCase: LoadComicDataUseCase,
    val markComicAsReadUseCase: MarkComicAsReadUseCase,
    val markComicAsUnreadUseCase: MarkComicAsUnreadUseCase
)