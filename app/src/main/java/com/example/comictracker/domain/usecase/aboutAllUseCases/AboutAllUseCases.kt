package com.example.comictracker.domain.usecase.aboutAllUseCases

data class AboutAllUseCases(
    val loadAllCharacterSeriesUseCase: LoadAllCharacterSeriesUseCase,
    val loadAllCharactersUseCase: LoadAllCharactersUseCase,
    val loadAllCurrentReadingSeriesUseCase: LoadAllCurrentReadingSeriesUseCase,
    val loadAllDiscoverSeriesUseCase: LoadAllDiscoverSeriesUseCase,
    val loadAllLastComicUseCase: LoadAllLastComicUseCase,
    val loadAllLibComicUseCase: LoadAllLibComicUseCase,
    val loadAllLibSeriesUseCase: LoadAllLibSeriesUseCase,
    val loadAllMayLikeSeriesUseCase: LoadAllMayLikeSeriesUseCase,
    val loadAllNewComicUseCase: LoadAllNewComicUseCase,
    val loadAllNextComicUseCase: LoadAllNextComicUseCase,
    val loadAllReadListUseCase: LoadAllReadListUseCase
)