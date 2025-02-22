package com.example.comictracker.data.api

interface MarvelComicApi {


    fun getSeries()

    fun getComic()

    fun getCharacter()

    fun getComicListFromSeries()

    fun getSeriesByCharacter()

    fun getNewReleases()

    fun getNewSeries()

    fun getAllCharacters()

    fun getComicsWithCharacter()

    fun getConnectedComics()

    fun searchSeries()
}