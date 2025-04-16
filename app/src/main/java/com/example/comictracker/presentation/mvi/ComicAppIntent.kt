package com.example.comictracker.presentation.mvi

/**
 * Comic app intent
 *
 * @constructor Create empty Comic app intent
 */
sealed class ComicAppIntent {
    data object LoadHomeScreen: ComicAppIntent()

    data object LoadLibraryScreen: ComicAppIntent()

    data object LoadSearchScreen: ComicAppIntent()

    /**
     * Load all screen
     *
     * @property sourceId
     * @property sourceCat
     * @property loadedCount
     * @constructor Create empty Load all screen
     */
    data class LoadAllScreen(val sourceId: Int, val sourceCat:String, val loadedCount: Int ): ComicAppIntent()

    /**
     * Load all characters screen
     *
     * @property loadedCount
     * @constructor Create empty Load all characters screen
     */
    data class LoadAllCharactersScreen(val loadedCount: Int ): ComicAppIntent()

    /**
     * Search
     *
     * @property query
     * @constructor Create empty Search
     */
    data class Search(val query:String): ComicAppIntent()

    /**
     * Load series screen
     *
     * @property seriesId
     * @constructor Create empty Load series screen
     */
    data class LoadSeriesScreen(val seriesId:Int): ComicAppIntent()

    /**
     * Load comic from series screen
     *
     * @property seriesId
     * @property loadCount
     * @constructor Create empty Load comic from series screen
     */
    data class LoadComicFromSeriesScreen(val seriesId: Int, val loadCount: Int): ComicAppIntent()

    /**
     * Load comic screen
     *
     * @property comicId
     * @constructor Create empty Load comic screen
     */
    data class LoadComicScreen(val comicId:Int): ComicAppIntent()

    /**
     * Load character screen
     *
     * @property characterId
     * @constructor Create empty Load character screen
     */
    data class LoadCharacterScreen(val characterId:Int): ComicAppIntent()

    /**
     * Mark as read series
     *
     * @property seriesId
     * @constructor Create empty Mark as read series
     */
    data class MarkAsReadSeries(val seriesId:Int): ComicAppIntent()

    /**
     * Add series to favorite
     *
     * @property seriesId
     * @constructor Create empty Add series to favorite
     */
    data class AddSeriesToFavorite(val seriesId:Int): ComicAppIntent()

    /**
     * Remove series from favorite
     *
     * @property seriesId
     * @constructor Create empty Remove series from favorite
     */
    data class RemoveSeriesFromFavorite(val seriesId:Int): ComicAppIntent()

    /**
     * Mark as will be read series
     *
     * @property seriesId
     * @constructor Create empty Mark as will be read series
     */
    data class MarkAsWillBeReadSeries(val seriesId:Int): ComicAppIntent()

    /**
     * Mark as currently reading series
     *
     * @property seriesId
     * @property firstIssueId
     * @constructor Create empty Mark as currently reading series
     */
    data class MarkAsCurrentlyReadingSeries(val seriesId:Int, val firstIssueId:Int?): ComicAppIntent()

    /**
     * Mark as unread series
     *
     * @property seriesId
     * @constructor Create empty Mark as unread series
     */
    data class MarkAsUnreadSeries(val seriesId:Int): ComicAppIntent()

    /**
     * Mark as read comic
     *
     * @property comicId
     * @property seriesId
     * @property issueNumber
     * @constructor Create empty Mark as read comic
     */
    data class MarkAsReadComic(val comicId:Int, val seriesId: Int, val issueNumber:String): ComicAppIntent()

    /**
     * Mark as unread comic
     *
     * @property comicId
     * @property seriesId
     * @property issueNumber
     * @constructor Create empty Mark as unread comic
     */
    data class MarkAsUnreadComic(val comicId:Int, val seriesId: Int, val issueNumber:String): ComicAppIntent()

    /**
     * Mark as read comic in list
     *
     * @property comicId
     * @property seriesId
     * @property issueNumber
     * @property loadedCount
     * @constructor Create empty Mark as read comic in list
     */
    data class MarkAsReadComicInList(val comicId:Int, val seriesId: Int, val issueNumber:String, val loadedCount: Int?): ComicAppIntent()

    /**
     * Mark as unread comic in list
     *
     * @property comicId
     * @property seriesId
     * @property issueNumber
     * @property loadedCount
     * @constructor Create empty Mark as unread comic in list
     */
    data class MarkAsUnreadComicInList(val comicId:Int, val seriesId: Int, val issueNumber:String, val loadedCount: Int): ComicAppIntent()


}