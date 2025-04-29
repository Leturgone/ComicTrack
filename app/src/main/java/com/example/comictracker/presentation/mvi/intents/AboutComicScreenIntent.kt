package com.example.comictracker.presentation.mvi.intents

import com.example.comictracker.presentation.mvi.ComicAppIntent

sealed class AboutComicScreenIntent {
    data class LoadComicScreen(val comicId:Int): AboutComicScreenIntent()

    data class MarkAsReadComic(val comicId:Int,val seriesId: Int, val issueNumber:String): AboutComicScreenIntent()

    data class MarkAsUnreadComic(val comicId:Int,val seriesId: Int, val issueNumber:String): AboutComicScreenIntent()

    data class MarkAsReadComicInList(val comicId:Int,val seriesId: Int, val issueNumber:String,val loadedCount: Int?): AboutComicScreenIntent()

    data class MarkAsUnreadComicInList(val comicId:Int,val seriesId: Int, val issueNumber:String,val loadedCount: Int): AboutComicScreenIntent()
}