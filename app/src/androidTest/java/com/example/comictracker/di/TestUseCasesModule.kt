package com.example.comictracker.di

import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.example.comictracker.domain.usecase.aboutAllUseCases.AboutAllUseCases
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllCharacterSeriesUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllCharactersUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllCurrentReadingSeriesUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllDiscoverSeriesUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllLastComicUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllLibComicUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllLibSeriesUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllMayLikeSeriesUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllNewComicUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllNextComicUseCase
import com.example.comictracker.domain.usecase.aboutAllUseCases.LoadAllReadListUseCase
import com.example.comictracker.domain.usecase.aboutCharacterUseCases.AboutCharacterUseCases
import com.example.comictracker.domain.usecase.aboutCharacterUseCases.LoadCharacterDataUseCase
import com.example.comictracker.domain.usecase.aboutCharacterUseCases.LoadSeriesWithCharacterUseCase
import com.example.comictracker.domain.usecase.aboutComicUseCases.AboutComicUseCases
import com.example.comictracker.domain.usecase.aboutComicUseCases.LoadComicCharactersUseCase
import com.example.comictracker.domain.usecase.aboutComicUseCases.LoadComicCreatorsUseCase
import com.example.comictracker.domain.usecase.aboutComicUseCases.LoadComicDataUseCase
import com.example.comictracker.domain.usecase.aboutComicUseCases.MarkComicAsReadUseCase
import com.example.comictracker.domain.usecase.aboutComicUseCases.MarkComicAsUnreadUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.AboutSeriesUseCases
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.AddSeriesToFavoritesUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.LoadComicsListUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.LoadConnectedSeriesUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.LoadNextComicInSeriesUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.LoadSeriesCharactersUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.LoadSeriesCreatorsUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.LoadSeriesDataUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.MarkNextComicAsReadUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.MarkNextComicAsUnreadUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.MarkSeriesAsCurrentlyReadingUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.MarkSeriesAsReadUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.MarkSeriesAsUnreadUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.MarkSeriesAsWillBeReadUseCase
import com.example.comictracker.domain.usecase.aboutSeriesUseCases.RemoveSeriesFromFavoritesUseCase
import com.example.comictracker.domain.usecase.comicFromSeriesUseCases.ComicFromSeriesUseCases
import com.example.comictracker.domain.usecase.comicFromSeriesUseCases.LoadComicListFromSeriesWithMarksUseCase
import com.example.comictracker.domain.usecase.comicFromSeriesUseCases.MarkComicAsReadInComicListUseCase
import com.example.comictracker.domain.usecase.comicFromSeriesUseCases.MarkComicAsUnreadInComicListUseCase
import com.example.comictracker.domain.usecase.homeUseCases.HomeUseCases
import com.example.comictracker.domain.usecase.homeUseCases.LoadCurrentNextComicsUseCase
import com.example.comictracker.domain.usecase.homeUseCases.LoadNewComicsUseCase
import com.example.comictracker.domain.usecase.libraryUseCases.LibraryUseCases
import com.example.comictracker.domain.usecase.libraryUseCases.LoadCurrentlyReadingSeriesUseCase
import com.example.comictracker.domain.usecase.libraryUseCases.LoadFavoriteSeriesUseCase
import com.example.comictracker.domain.usecase.libraryUseCases.LoadHistoryReadComicUseCase
import com.example.comictracker.domain.usecase.libraryUseCases.LoadStatisticsUseCase
import com.example.comictracker.domain.usecase.searchUseCases.LoadCharactersListUseCase
import com.example.comictracker.domain.usecase.searchUseCases.LoadCharactersSearchResultListUseCase
import com.example.comictracker.domain.usecase.searchUseCases.LoadMayLikeSeriesListUseCase
import com.example.comictracker.domain.usecase.searchUseCases.LoadNewSeriesListUseCase
import com.example.comictracker.domain.usecase.searchUseCases.LoadSeriesSearchResultListUseCase
import com.example.comictracker.domain.usecase.searchUseCases.SearchUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestUseCasesModule {

    @Provides
    @Singleton
    fun provideAboutAllUseCases(
        remoteSeriesRepository: RemoteSeriesRepository,
        remoteComicsRepository: RemoteComicsRepository,
        remoteCharacterRepository: RemoteCharacterRepository,
        localReadRepository: LocalReadRepository
    ):AboutAllUseCases{
        return AboutAllUseCases(
            loadAllCharacterSeriesUseCase = LoadAllCharacterSeriesUseCase(remoteSeriesRepository),
            loadAllCharactersUseCase = LoadAllCharactersUseCase(remoteCharacterRepository),
            loadAllCurrentReadingSeriesUseCase = LoadAllCurrentReadingSeriesUseCase(remoteSeriesRepository,localReadRepository),
            loadAllDiscoverSeriesUseCase = LoadAllDiscoverSeriesUseCase(remoteSeriesRepository),
            loadAllLastComicUseCase = LoadAllLastComicUseCase(remoteComicsRepository,localReadRepository),
            loadAllLibComicUseCase = LoadAllLibComicUseCase(remoteComicsRepository,localReadRepository),
            loadAllLibSeriesUseCase = LoadAllLibSeriesUseCase(remoteSeriesRepository, localReadRepository),
            loadAllMayLikeSeriesUseCase = LoadAllMayLikeSeriesUseCase(remoteSeriesRepository, localReadRepository),
            loadAllNewComicUseCase = LoadAllNewComicUseCase(remoteComicsRepository, localReadRepository),
            loadAllNextComicUseCase = LoadAllNextComicUseCase(remoteComicsRepository, localReadRepository),
            loadAllReadListUseCase = LoadAllReadListUseCase(remoteSeriesRepository, localReadRepository)
        )
    }


    @Provides
    @Singleton
    fun providesAboutCharacterUseCases(
        characterRepository: RemoteCharacterRepository,
        seriesRepository: RemoteSeriesRepository
    ): AboutCharacterUseCases {
        return AboutCharacterUseCases(
            loadCharacterDataUseCase = LoadCharacterDataUseCase(characterRepository),
            loadSeriesWithCharacterUseCase = LoadSeriesWithCharacterUseCase(seriesRepository)
        )
    }


    @Provides
    @Singleton
    fun providesAboutComicUseCases(
        remoteComicsRepository: RemoteComicsRepository,
        remoteCharacterRepository: RemoteCharacterRepository,
        remoteCreatorsRepository: RemoteCreatorsRepository,
        localWriteRepository: LocalWriteRepository,
        localReadRepository: LocalReadRepository
    ): AboutComicUseCases {
        return AboutComicUseCases(
            loadComicCharactersUseCase = LoadComicCharactersUseCase(remoteCharacterRepository),
            loadComicCreatorsUseCase = LoadComicCreatorsUseCase(remoteCreatorsRepository),
            loadComicDataUseCase = LoadComicDataUseCase(remoteComicsRepository, localReadRepository),
            markComicAsReadUseCase = MarkComicAsReadUseCase(remoteComicsRepository, localWriteRepository),
            markComicAsUnreadUseCase = MarkComicAsUnreadUseCase(remoteComicsRepository, localWriteRepository)
        )
    }


    @Provides
    @Singleton
    fun provideAboutSeriesUseCases(
        remoteSeriesRepository: RemoteSeriesRepository,
        remoteComicsRepository:RemoteComicsRepository,
        remoteCharacterRepository: RemoteCharacterRepository,
        remoteCreatorsRepository: RemoteCreatorsRepository,
        localWriteRepository: LocalWriteRepository,
        localReadRepository: LocalReadRepository
    ): AboutSeriesUseCases {
        return AboutSeriesUseCases(
            loadComicsListUseCase = LoadComicsListUseCase(remoteComicsRepository),
            loadConnectedSeriesUseCase = LoadConnectedSeriesUseCase(remoteSeriesRepository),
            loadNextComicInSeriesUseCase = LoadNextComicInSeriesUseCase(remoteComicsRepository, localReadRepository),
            loadSeriesCharactersUseCase = LoadSeriesCharactersUseCase(remoteCharacterRepository),
            loadSeriesCreatorsUseCase = LoadSeriesCreatorsUseCase(remoteCreatorsRepository),
            loadSeriesDataUseCase = LoadSeriesDataUseCase(remoteSeriesRepository, localReadRepository),
            markSeriesAsCurrentlyReadingUseCase = MarkSeriesAsCurrentlyReadingUseCase(localWriteRepository),
            markSeriesAsReadUseCase = MarkSeriesAsReadUseCase(localWriteRepository),
            markSeriesAsUnreadUseCase = MarkSeriesAsUnreadUseCase(localWriteRepository),
            markSeriesAsWillBeReadUseCase = MarkSeriesAsWillBeReadUseCase(localWriteRepository),
            addSeriesToFavoritesUseCase = AddSeriesToFavoritesUseCase(localWriteRepository),
            removeSeriesFromFavoritesUseCase = RemoveSeriesFromFavoritesUseCase(localWriteRepository),
            markNextComicAsReadUseCase = MarkNextComicAsReadUseCase(remoteComicsRepository, localWriteRepository),
            markNextComicAsUnreadUseCase = MarkNextComicAsUnreadUseCase(remoteComicsRepository, localWriteRepository)
        )
    }


    @Provides
    @Singleton
    fun provideComicFromSeriesUseCases(
        remoteComicsRepository: RemoteComicsRepository,
        localWriteRepository: LocalWriteRepository,
        localReadRepository: LocalReadRepository
    ): ComicFromSeriesUseCases {
        return ComicFromSeriesUseCases(
            loadComicListFromSeriesWithMarksUseCase = LoadComicListFromSeriesWithMarksUseCase(remoteComicsRepository, localReadRepository),
            markComicAsReadInComicListUseCase = MarkComicAsReadInComicListUseCase(remoteComicsRepository, localWriteRepository),
            markComicAsUnreadInComicListUseCase = MarkComicAsUnreadInComicListUseCase(remoteComicsRepository, localWriteRepository)
        )
    }


    @Provides
    @Singleton
    fun providesHomeUseCases(
        remoteComicsRepository: RemoteComicsRepository,
        localReadRepository: LocalReadRepository
    ): HomeUseCases {
        return HomeUseCases(
            loadCurrentNextComicUseCase = LoadCurrentNextComicsUseCase(remoteComicsRepository, localReadRepository),
            loadNewComicsUseCase = LoadNewComicsUseCase(remoteComicsRepository, localReadRepository)
        )
    }


    @Provides
    @Singleton
    fun providesLibraryUseCases(
        remoteSeriesRepository: RemoteSeriesRepository,
        remoteComicsRepository: RemoteComicsRepository,
        localReadRepository: LocalReadRepository
    ): LibraryUseCases {
        return LibraryUseCases(
            loadCurrentlyReadingSeriesUseCase = LoadCurrentlyReadingSeriesUseCase(remoteSeriesRepository, localReadRepository),
            loadFavoriteSeriesUseCase = LoadFavoriteSeriesUseCase(remoteSeriesRepository, localReadRepository),
            loadHistoryReadComicUseCase = LoadHistoryReadComicUseCase(remoteComicsRepository, localReadRepository),
            loadStatisticsUseCase = LoadStatisticsUseCase(localReadRepository)
        )
    }


    @Provides
    @Singleton
    fun providesSearchUseCases(
        remoteSeriesRepository: RemoteSeriesRepository,
        remoteCharacterRepository: RemoteCharacterRepository,
        localReadRepository: LocalReadRepository
    ): SearchUseCases {
        return SearchUseCases(
            loadCharactersListUseCase = LoadCharactersListUseCase(remoteCharacterRepository),
            loadCharactersSearchResultListUseCase = LoadCharactersSearchResultListUseCase(remoteCharacterRepository),
            loadMayLikeSeriesListUseCase = LoadMayLikeSeriesListUseCase(remoteSeriesRepository, localReadRepository),
            loadNewSeriesListUseCase = LoadNewSeriesListUseCase(remoteSeriesRepository),
            loadSeriesSearchResultListUseCase = LoadSeriesSearchResultListUseCase(remoteSeriesRepository)
        )
    }
}