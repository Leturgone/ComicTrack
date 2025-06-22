package com.example.comictracker.domain.usecase.aboutSeriesUseCases

data class AboutSeriesUseCases(
    val loadComicsListUseCase: LoadComicsListUseCase,
    val loadConnectedSeriesUseCase: LoadConnectedSeriesUseCase,
    val loadNextComicInSeriesUseCase: LoadNextComicInSeriesUseCase,
    val loadSeriesCharactersUseCase: LoadSeriesCharactersUseCase,
    val loadSeriesCreatorsUseCase: LoadSeriesCreatorsUseCase,
    val loadSeriesDataUseCase: LoadSeriesDataUseCase,
    val markSeriesAsCurrentlyReadingUseCase: MarkSeriesAsCurrentlyReadingUseCase,
    val markSeriesAsReadUseCase: MarkSeriesAsReadUseCase,
    val markSeriesAsUnreadUseCase: MarkSeriesAsUnreadUseCase,
    val markSeriesAsWillBeReadUseCase: MarkSeriesAsWillBeReadUseCase,
    val addSeriesToFavoritesUseCase: AddSeriesToFavoritesUseCase,
    val removeSeriesFromFavoritesUseCase: RemoveSeriesFromFavoritesUseCase,
    val markNextComicAsReadUseCase: MarkNextComicAsReadUseCase,
    val markNextComicAsUnreadUseCase: MarkNextComicAsUnreadUseCase
)
