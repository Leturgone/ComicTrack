package com.example.comictracker.mvi

sealed class DataListState {
    data object Idle: DataListState()
    data object Loading: DataListState()
    data class Error(val errorMessage:String): DataListState()
    data class Success<out R>(val result:R): DataListState()

}