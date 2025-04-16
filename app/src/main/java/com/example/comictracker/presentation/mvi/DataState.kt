package com.example.comictracker.presentation.mvi

/**
 * Data state
 *
 * @param R
 * @constructor Create empty Data state
 */
sealed class DataState<out R> {
    data object Loading: DataState<Nothing>()

    /**
     * Error
     *
     * @property errorMessage
     * @constructor Create empty Error
     */
    data class Error(val errorMessage:String): DataState<Nothing>()

    /**
     * Success
     *
     * @param R
     * @property result
     * @constructor Create empty Success
     */
    data class Success<out R>(val result:R): DataState<R>()

}