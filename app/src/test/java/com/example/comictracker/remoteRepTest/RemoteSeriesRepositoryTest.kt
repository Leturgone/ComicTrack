package com.example.comictracker.remoteRepTest

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.seriesDTO.SeriesDTO
import com.example.comictracker.data.repository.remote.RemoteSeriesRepositoryImpl
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@RunWith(RobolectricTestRunner::class)
class RemoteSeriesRepositoryTest {
    private lateinit var api: MarvelComicApi
    private lateinit var remoteSeriesRepository: RemoteSeriesRepository
    private lateinit var seriesDTO: SeriesDTO
    private lateinit var series:SeriesModel

    @Before
    fun setUp(){
        val inputStream = javaClass.classLoader?.getResourceAsStream("IronManSeries.json")
            ?: throw IllegalStateException("Не найден файл IronManSeries.json в test/resources")

        val reader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
        seriesDTO = Gson().fromJson(reader, SeriesDTO::class.java)

        series = SeriesModel(
            seriesId=2029,
            title="Iron Man (1968 - 1996)",
            date="1968 - 1996",
            desc="The Armored Avenger battles super villains as well as corporate sabotage and his own personal demons in this landmark series! See the stories that formed the basis for Tony Stark becoming a household name!",
            image="http://i.annihil.us/u/prod/marvel/i/mg/1/d0/519bad24bebcd.jpg",
            comics= listOf(
                9327, 9438, 9549, 9593, 9604, 9615, 9626,
                9637, 9648, 9328, 9339, 9350, 9361, 9372,
                9383, 9394, 9405, 9416, 9427, 9439),
            creators= listOf(
                Pair(1186, "inker"), Pair(3389, "inker"), Pair(2707, "inker"),
                Pair(1258, "inker"), Pair(263, "inker"), Pair(300, "inker"),
                Pair(726, "inker"), Pair(2722, "inker"), Pair(1107, "writer"),
                Pair(3054, "writer"), Pair(1865, "letterer"), Pair(3626, "letterer"),
                Pair(1950, "colorist"), Pair(1935, "colorist"), Pair(1167, "penciler"),
                Pair(506, "penciler"), Pair(1288, "penciler"), Pair(211, "penciler"),
                Pair(2376, "penciler"), Pair(4661, "penciler")
            ),
            characters= listOf(
                1009144, 1010801, 1009165, 1009175, 1009179, 1011907,
                1009186, 1009189, 1010881, 1009198, 1009167, 1009220,
                1010346, 1009251, 1009255, 1009262, 1009270, 1010890,
                1009273, 1009281
            ),
            connectedSeries= listOf(13577, 2079),
            readMark="",
            favoriteMark=false
        )
        api = Mockito.mock(MarvelComicApi::class.java)
        remoteSeriesRepository = RemoteSeriesRepositoryImpl(api)
    }

    @Test
    fun getCharacterSeriesTest() = runTest{
        Mockito.`when`(
            api.getCharacterSeries(characterId = "11", offset = "0")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.getCharacterSeries(11,0)
        assertEquals(listOf(series),result)
    }

    @Test
    fun getAllSeriesTest() = runTest{
        Mockito.`when`(
            api.getAllSeries(offset = "0")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.getAllSeries(0)
        assertEquals(listOf(series),result)
    }

    @Test
    fun getSeriesByTitleTest() = runTest{
        Mockito.`when`(
            api.getSeriesByTitle("Iron Man")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.getSeriesByTitle("Iron Man")
        assertEquals(listOf(series),result)
    }

    @Test
    fun getSeriesByIdTest() = runTest{
        Mockito.`when`(
            api.getSeriesById("11")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.getSeriesById(11)
        assertEquals(series,result)
    }

    @Test
    fun getConnectedSeriesTest() = runTest{
        Mockito.`when`(
            api.getSeriesById("11")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.getConnectedSeries(listOf(11,null))
        assertEquals(listOf(series),result)
    }

    @Test
    fun loadMayLikeSeriesIdsTest() = runTest{
        Mockito.`when`(
            api.getSeriesById("11")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.loadMayLikeSeriesIds(listOf(11))
        assertEquals(series.connectedSeries,result)
    }

    @Test
    fun fetchSeriesTest() = runTest{
        Mockito.`when`(
            api.getSeriesById("11")
        ).thenReturn(seriesDTO)

        val result = remoteSeriesRepository.fetchSeries(listOf(11,12))
        assertEquals(listOf(series),result)
    }
}