package com.example.comictracker.presentation.mvi.intents

import com.example.comictracker.presentation.mvi.ComicAppIntent

sealed class ComicFromSeriesScreenIntent {

    data class LoadComicFromSeriesScreen(val seriesId: Int, val loadCount: Int): ComicFromSeriesScreenIntent()

    data class MarkAsReadComicInList(val comicId:Int,val seriesId: Int, val issueNumber:String,val loadedCount: Int?): ComicFromSeriesScreenIntent()

    data class MarkAsUnreadComicInList(val comicId:Int,val seriesId: Int, val issueNumber:String,val loadedCount: Int): ComicFromSeriesScreenIntent()

}