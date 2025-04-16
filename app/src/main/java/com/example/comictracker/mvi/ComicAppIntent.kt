package com.example.comictracker.mvi

sealed class ComicAppIntent {
    data object LoadHomeScreen: ComicAppIntent()

    data object LoadLibraryScreen: ComicAppIntent()

    data object LoadSearchScreen: ComicAppIntent()

    data class LoadAllScreen(val sourceId: Int, val sourceCat:String, val loadedCount: Int ): ComicAppIntent()

    data class LoadAllCharactersScreen(val loadedCount: Int ): ComicAppIntent()

    data class Search(val query:String): ComicAppIntent()

    data class LoadSeriesScreen(val seriesId:Int): ComicAppIntent()

    data class LoadComicFromSeriesScreen(val seriesId: Int, val loadCount: Int):ComicAppIntent()

    data class LoadComicScreen(val comicId:Int):ComicAppIntent()

    data class LoadCharacterScreen(val characterId:Int):ComicAppIntent()

    data class MarkAsReadSeries(val seriesId:Int): ComicAppIntent()

    data class AddSeriesToFavorite(val seriesId:Int):ComicAppIntent()

    data class RemoveSeriesFromFavorite(val seriesId:Int):ComicAppIntent()

    data class MarkAsWillBeReadSeries(val seriesId:Int): ComicAppIntent()

    data class MarkAsCurrentlyReadingSeries(val seriesId:Int,val firstIssueId:Int?): ComicAppIntent()

    data class MarkAsUnreadSeries(val seriesId:Int): ComicAppIntent()

    data class MarkAsReadComic(val comicId:Int,val seriesId: Int, val issueNumber:String): ComicAppIntent()

    data class MarkAsUnreadComic(val comicId:Int,val seriesId: Int, val issueNumber:String): ComicAppIntent()

    data class MarkAsReadComicInList(val comicId:Int,val seriesId: Int, val issueNumber:String,val loadedCount: Int?): ComicAppIntent()

    data class MarkAsUnreadComicInList(val comicId:Int,val seriesId: Int, val issueNumber:String,val loadedCount: Int): ComicAppIntent()


}