package com.example.comictracker.domain.usecase.comicFromSeriesUseCases

data class ComicFromSeriesUseCases(
    val loadComicListFromSeriesWithMarksUseCase: LoadComicListFromSeriesWithMarksUseCase,
    val markComicAsReadInComicListUseCase: MarkComicAsReadInComicListUseCase,
    val markComicAsUnreadInComicListUseCase: MarkComicAsUnreadInComicListUseCase
)
