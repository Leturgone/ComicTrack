package com.example.comictracker.presentation.mvi.intents

sealed class AllScreenIntent {
    data class LoadAllScreen(val sourceId: Int, val sourceCat:String, val loadedCount: Int ): AllScreenIntent()

    data class LoadAllCharactersScreen(val loadedCount: Int ): AllScreenIntent()

    data class LoadAllCharacterSeriesScreen(val characterId:Int, val loadedCount:Int):AllScreenIntent()

    data class LoadAllDiscoverSeriesScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllMayLikeSeriesScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllNextComicScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllNewComicScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllLastComicScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllCurrentReadingSeriesScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllLibComicScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadAllLibSeriesScreen(val loadedCount: Int):AllScreenIntent()

    data class LoadReadListScreen(val loadedCount: Int):AllScreenIntent()
}