package com.example.comictracker.mvi

sealed class DataState<out R> {
    data object Loading: DataState<Nothing>()
    data class Error(val errorMessage:String): DataState<Nothing>()
    data class Success<out R>(val result:R): DataState<R>()

}