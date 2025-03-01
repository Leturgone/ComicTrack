package com.example.comictracker.mvi

sealed class ComicAppIntent {
    data object LoadHomeScreen: ComicAppIntent()
    data object LoadProfileScreen: ComicAppIntent()
    data object LoadSearchScreen: ComicAppIntent()
    data class Search(val query:String): ComicAppIntent()
    data class LoadSeries(val seriesId:Int): ComicAppIntent()
    data class LoadComic(val comicId:Int):ComicAppIntent()
    data class LoadCharacter(val characterId:Int):ComicAppIntent()
    data class MarkAsReadSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsWillBeReadSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsCurrentlyReadingSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsUnreadSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsReadComic(val comicId:Int): ComicAppIntent()
    data class MarkAsUnreadComic(val comicId:Int): ComicAppIntent()


}