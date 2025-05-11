package com.example.comictracker.remoteRepTest

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.charactersDTO.CharactersDTO
import com.example.comictracker.data.api.dto.comicsDTO.ComicsDTO
import com.example.comictracker.data.repository.remote.RemoteCharacterRepositoryImpl
import com.example.comictracker.data.repository.remote.RemoteComicsRepositoryImpl
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
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
class RemoteComicsRepositoryUnitTests {
    private lateinit var api: MarvelComicApi
    private lateinit var remoteComicsRepository: RemoteComicsRepository
    private lateinit var comicsDTO: ComicsDTO
    private lateinit var comic:ComicModel

    @Before
    fun setUp(){
        val inputStream = javaClass.classLoader?.getResourceAsStream("IronManComics.json")
            ?: throw IllegalStateException("Не найден файл IronManComics.json в test/resources")


        val reader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
        comicsDTO = Gson().fromJson(reader, ComicsDTO::class.java)

        comic = ComicModel(
            comicId=9327,
            title="Iron Man (1968) #1",
            number="1",
            image="http://i.annihil.us/u/prod/marvel/i/mg/1/c0/6467d294959d6.jpg",
            seriesId=2029,
            seriesTitle="Iron Man (1968 - 1996)",
            date="01.05.1968",
            creators= listOf(
                Pair(270, "penciler"), Pair(1178, "writer"),
                Pair(13093, "letterer")
            ),
            characters= listOf(1009368),
            readMark="unread"
        )

        api = Mockito.mock(MarvelComicApi::class.java)
        remoteComicsRepository = RemoteComicsRepositoryImpl(api)

    }

    @Test
    fun getSeriesLastReleasesByIdTest() = runTest{
        Mockito.`when`(
            api.getSeriesLastReleasesById(series = "11")
        ).thenReturn(comicsDTO)

        val result = remoteComicsRepository.getSeriesLastReleasesById(11)
        assertEquals(listOf(comic),result)
    }

    @Test
    fun getComicsFromSeriesTest()= runTest{
        Mockito.`when`(
            api.getComicsFromSeries("11", offset = "0")
        ).thenReturn(comicsDTO)

        val result = remoteComicsRepository.getComicsFromSeries(11,0)
        assertEquals(listOf(comic),result)
    }

    @Test
    fun getComicByIdTest() = runTest{
        Mockito.`when`(
            api.getComicById("11")
        ).thenReturn(comicsDTO)

        val result = remoteComicsRepository.getComicById(11)
        assertEquals(comic,result)
    }

    @Test
    fun fetchComicsTest() = runTest{
        Mockito.`when`(
            api.getComicById("11")
        ).thenReturn(comicsDTO)

        Mockito.`when`(
            api.getComicById("12")
        ).thenThrow(RuntimeException("Exception"))

        val result = remoteComicsRepository.fetchComics(listOf(11,12))
        assertEquals(listOf(comic),result)
    }

    fun fetchUpdatesForSeriesTest(){}

    fun getPreviousComicIdTest(){}

    fun getNextComicIdTest(){}

}