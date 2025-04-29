package com.example.comictracker.presentation.mvi.intents

sealed class AboutComicScreenIntent {
    data class LoadComicScreen(val comicId:Int): AboutComicScreenIntent()

    data class MarkAsReadComic(val comicId:Int,val seriesId: Int, val issueNumber:String): AboutComicScreenIntent()

    data class MarkAsUnreadComic(val comicId:Int,val seriesId: Int, val issueNumber:String): AboutComicScreenIntent()
}