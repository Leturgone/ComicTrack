package com.example.comictracker.mvi

import com.example.comictracker.domain.model.CharacterModel

sealed class ComicAppIntent {
    data object LoadHomeScreen: ComicAppIntent()
    data object LoadProfileScreen: ComicAppIntent()
    data object LoadSearchScreen: ComicAppIntent()
    data class Search(val query:String): ComicAppIntent()
    data class LoadSeriesScreen(val seriesId:Int): ComicAppIntent()
    data class LoadComicFromSeriesScreen(val seriesId: Int):ComicAppIntent()
    data class LoadComicScreen(val comicId:Int):ComicAppIntent()
    data class LoadCharacterScreen(val character:CharacterModel):ComicAppIntent()
    data class MarkAsReadSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsWillBeReadSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsCurrentlyReadingSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsUnreadSeries(val seriesId:Int): ComicAppIntent()
    data class MarkAsReadComic(val comicId:Int): ComicAppIntent()
    data class MarkAsUnreadComic(val comicId:Int): ComicAppIntent()


}