package com.example.comictracker.domain.repository.remote

import com.example.comictracker.domain.model.ComicModel

interface RemoteComicsRepository {

    suspend fun getSeriesLastReleasesById(id: Int): Result<List<ComicModel>>

    suspend fun getComicsFromSeries(seriesId: Int,loadedCount: Int = 0): Result<List<ComicModel>>

    suspend fun getComicById(comicId:Int): Result<ComicModel>

    suspend fun fetchComics(ids: List<Int>): Result<List<ComicModel>>

    suspend fun fetchUpdatesForSeries(ids: List<Int>): Result<List<ComicModel>>

    suspend fun getPreviousComicId(seriesId: Int, number: Int): Result<Int?>

    suspend fun getNextComicId(seriesId: Int, number: Int): Result<Int?>
}