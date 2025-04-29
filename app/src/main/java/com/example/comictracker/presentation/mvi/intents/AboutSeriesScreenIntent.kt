package com.example.comictracker.presentation.mvi.intents

import com.example.comictracker.presentation.mvi.ComicAppIntent

sealed class AboutSeriesScreenIntent {
    data class LoadSeriesScreen(val seriesId:Int): AboutComicScreenIntent()

    data class MarkAsReadSeries(val seriesId:Int): AboutComicScreenIntent()

    data class AddSeriesToFavorite(val seriesId:Int): AboutComicScreenIntent()

    data class RemoveSeriesFromFavorite(val seriesId:Int): AboutComicScreenIntent()

    data class MarkAsWillBeReadSeries(val seriesId:Int): AboutComicScreenIntent()

    data class MarkAsCurrentlyReadingSeries(val seriesId:Int,val firstIssueId:Int?): AboutComicScreenIntent()
    data class MarkAsUnreadSeries(val seriesId:Int): AboutComicScreenIntent()
}