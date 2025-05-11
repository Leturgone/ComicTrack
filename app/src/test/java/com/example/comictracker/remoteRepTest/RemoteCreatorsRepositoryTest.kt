package com.example.comictracker.remoteRepTest

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.comicsDTO.ComicsDTO
import com.example.comictracker.data.api.dto.creatorsDTO.CreatorsDTO
import com.example.comictracker.data.repository.remote.RemoteCreatorsRepositoryImpl
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
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
class RemoteCreatorsRepositoryTest {
    private lateinit var api: MarvelComicApi
    private lateinit var remoteCreatorsRepository: RemoteCreatorsRepository
    private lateinit var creatorsDTO: CreatorsDTO
    private lateinit var creator:CreatorModel

    @Before
    fun setUp(){
        val inputStream = javaClass.classLoader?.getResourceAsStream("IronManCreator.json")
            ?: throw IllegalStateException("Не найден файл IronManCreator.json в test/resources")


        val reader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
        creatorsDTO = Gson().fromJson(reader, CreatorsDTO::class.java)

        creator = CreatorModel(
            creatorId=2707,
            name="Jeff Albrecht",
            image="http://i.annihil.us/u/prod/marvel/i/mg/3/c0/4c3652d069f65.jpg",
            role="inker")

        api = Mockito.mock(MarvelComicApi::class.java)
        remoteCreatorsRepository = RemoteCreatorsRepositoryImpl(api)
    }


    @Test
    fun getSeriesCreatorsTest() = runTest{
        Mockito.`when`(
            api.getCreatorById("2707")
        ).thenReturn(creatorsDTO)

        val result = remoteCreatorsRepository.getSeriesCreators(listOf(Pair(2707,"inker")))
        assertEquals(listOf(creator),result)
    }

    @Test
    fun getComicCreatorsTest() = runTest{
        Mockito.`when`(
            api.getCreatorById("2707")
        ).thenReturn(creatorsDTO)

        val result = remoteCreatorsRepository.getComicCreators(listOf(Pair(2707,"inker")))
        assertEquals(listOf(creator),result)
    }

}