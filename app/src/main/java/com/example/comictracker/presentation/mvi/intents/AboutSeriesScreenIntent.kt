package com.example.comictracker.presentation.mvi.intents

sealed class AboutSeriesScreenIntent {

    data class LoadSeriesScreen(val seriesId:Int): AboutSeriesScreenIntent()

    data class MarkAsReadSeries(val seriesId:Int): AboutSeriesScreenIntent()

    data class AddSeriesToFavorite(val seriesId:Int): AboutSeriesScreenIntent()

    data class RemoveSeriesFromFavorite(val seriesId:Int): AboutSeriesScreenIntent()

    data class MarkAsWillBeReadSeries(val seriesId:Int): AboutSeriesScreenIntent()

    data class MarkAsCurrentlyReadingSeries(val seriesId:Int,val firstIssueId:Int?): AboutSeriesScreenIntent()

    data class MarkAsUnreadSeries(val seriesId:Int): AboutSeriesScreenIntent()

    data class MarkAsReadNextComic(val comicId:Int,val seriesId: Int, val issueNumber:String): AboutSeriesScreenIntent()

    data class MarkAsUnreadLastComic(val comicId:Int, val seriesId: Int, val issueNumber:String): AboutSeriesScreenIntent()

}