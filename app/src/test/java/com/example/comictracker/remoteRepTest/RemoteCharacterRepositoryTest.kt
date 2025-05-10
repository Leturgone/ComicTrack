package com.example.comictracker.remoteRepTest

import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.charactersDTO.CharactersDTO
import com.example.comictracker.data.repository.remote.RemoteCharacterRepositoryImpl
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RemoteCharacterRepositoryTest {
    private lateinit var api:MarvelComicApi
    private lateinit var remoteCharacterRepository: RemoteCharacterRepository
    private lateinit var charactersDTO: CharactersDTO
    private lateinit var character:CharacterModel

    @Before
    fun setUp(){
        val inputStream = javaClass.classLoader?.getResourceAsStream("IronManCharacter.json")
            ?: throw IllegalStateException("Не найден файл comic.json в test/resources")


        val reader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
        charactersDTO = Gson().fromJson(reader, CharactersDTO::class.java)

        character = CharacterModel(
            characterId=1009368,
            name="Iron Man",
            desc="Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man.",
            image="http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg",
            series= listOf(21112, 16450, 6079, 27392, 9790, 24380, 13896, 4897, 20443, 2116, 454, 1489, 2984, 41609, 318, 6056, 14818, 14779, 42695, 9792)
        )



        api = Mockito.mock(MarvelComicApi::class.java)
        remoteCharacterRepository = RemoteCharacterRepositoryImpl(api)

    }

    @Test
    fun getAllCharactersTest() = runTest{
        Mockito.`when`(
            api.getAllCharacters(offset = "0")
        ).thenReturn(charactersDTO)

        val result = remoteCharacterRepository.getAllCharacters(0)
        assertEquals(listOf(character),result)
    }

    @Test
    fun getCharactersByNameTest() = runTest{
        Mockito.`when`(
            api.getCharactersByName("Iron Man")
        ).thenReturn(charactersDTO)

        val result = remoteCharacterRepository.getCharactersByName("Iron Man")
        assertEquals(listOf(character),result)
    }

    @Test
    fun getSeriesCharactersTest() = runTest{
        Mockito.`when`(
            api.getSeriesCharacters("11")
        ).thenReturn(charactersDTO)

        val result = remoteCharacterRepository.getSeriesCharacters(11)
        assertEquals(listOf(character),result)
    }

    @Test
    fun getCharacterByIdTest(){}

    @Test
    fun getComicCharactersTest(){}
}