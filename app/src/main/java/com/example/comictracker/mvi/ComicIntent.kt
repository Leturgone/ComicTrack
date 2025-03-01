package com.example.comictracker.mvi

sealed class ComicIntent {
    data object LoadNewReleasesList: ComicIntent()
    data object LoadContinueReadingList: ComicIntent()
    data object LoadMayLikeList: ComicIntent()
    data object LoadDiscoverSeriesList: ComicIntent()
    data object LoadCharactersSec: ComicIntent()
    data object LoadFavorites: ComicIntent()
    data object LoadCurrentlyReading: ComicIntent()
    data object LoadLastUpdates: ComicIntent()
    data class Search(val query:String): ComicIntent()
    data class LoadSeries(val seriesId:Int): ComicIntent()
    data class LoadComic(val comicId:Int):ComicIntent()
    data class LoadCharacter(val characterId:Int):ComicIntent()
    data class MarkAsReadSeries(val seriesId:Int): ComicIntent()
    data class MarkAsWillBeReadSeries(val seriesId:Int): ComicIntent()
    data class MarkAsCurrentlyReadingSeries(val seriesId:Int): ComicIntent()
    data class MarkAsUnreadSeries(val seriesId:Int): ComicIntent()
    data class MarkAsReadComic(val comicId:Int): ComicIntent()
    data class MarkAsUnreadComic(val comicId:Int): ComicIntent()


}