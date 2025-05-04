package com.example.comictracker

import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import org.mockito.Mockito

class MockHelper(
    private val remoteSeriesRepository: RemoteSeriesRepository? = null,
    private val remoteComicsRepository: RemoteComicsRepository? = null,
    private val remoteCharacterRepository: RemoteCharacterRepository? = null,
    private val remoteCreatorsRepository: RemoteCreatorsRepository? = null,
    private val localWriteRepository: LocalWriteRepository? = null,
    private val localReadRepository: LocalReadRepository? = null
){
    suspend fun mockSeriesSetUp(series: SeriesModel){
        remoteSeriesRepository?.let {
            Mockito.`when`(
                it.getSeriesById(series.seriesId)
            ).thenReturn(series)

            Mockito.`when`(
                it.getConnectedSeries(series.connectedSeries)
            ).thenReturn(emptyList())
        }

        remoteComicsRepository?.let {
            Mockito.`when`(
                it.getComicsFromSeries(series.seriesId)
            ).thenReturn(emptyList())
        }

        remoteCharacterRepository?.let {
            Mockito.`when`(
                it.getSeriesCharacters(series.seriesId)
            ).thenReturn(emptyList())
        }

        remoteCreatorsRepository?.let {
            Mockito.`when`(
                it.getSeriesCreators(series.creators!!)
            ).thenReturn(emptyList())
        }

        localReadRepository?.let {
            Mockito.`when`(
                it.loadSeriesMark(series.seriesId)
            ).thenReturn("unread")

            Mockito.`when`(
                it.loadSeriesFavoriteMark(series.seriesId)
            ).thenReturn(false)

            Mockito.`when`(
                it.loadNextRead(series.seriesId)
            ).thenReturn(null)
        }


    }
}