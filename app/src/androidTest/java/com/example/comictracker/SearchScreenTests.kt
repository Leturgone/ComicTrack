package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.di.AppModule
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doThrow
import java.lang.Exception
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class SearchScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()


    private val mayLikeSeriesList = listOf(secondSeriesExample)

    private val discoverSeriesList = listOf(seriesExample)

    private val characterList = listOf(
        characterExample,
        characterExample.copy(name = "ch1"),
        characterExample.copy(name = "ch2"),
        characterExample.copy(name = "ch3")
    )

    @Inject
    lateinit var remoteComicRepository: RemoteComicsRepository

    @Inject
    lateinit var localReadRepository: LocalReadRepository

    @Inject
    lateinit var remoteCharacterRepository: RemoteCharacterRepository

    @Inject
    lateinit var remoteCreatorsRepository: RemoteCreatorsRepository

    @Inject
    lateinit var remoteSeriesRepository: RemoteSeriesRepository

    private lateinit var mockHelper:MockHelper



    @Before
    fun setUp() = runTest{
        hiltRule.inject()

        mockHelper = MockHelper(
            remoteSeriesRepository = remoteSeriesRepository,
            remoteComicsRepository = remoteComicRepository,
            remoteCharacterRepository = remoteCharacterRepository,
            remoteCreatorsRepository = remoteCreatorsRepository,
            localReadRepository = localReadRepository,
        )

        mockHelper.mockHomeScreenSetUp()


        //Discover list mock
        Mockito.`when`(
            remoteSeriesRepository.getAllSeries()
        ).thenReturn(discoverSeriesList)

        //MayLike list mock
        Mockito.`when`(
            localReadRepository.loadAllReadSeriesIds(0)
        ).thenReturn(listOf(1,2,3))

        Mockito.`when`(
            remoteSeriesRepository.loadMayLikeSeriesIds(listOf(1,2,3))
        ).thenReturn(listOf(1))


        Mockito.`when`(
            remoteSeriesRepository.fetchSeries(listOf(1))
        ).thenReturn(mayLikeSeriesList)

        //Character list mock
        Mockito.`when`(
            remoteCharacterRepository.getAllCharacters()
        ).thenReturn(characterList)
    }

    @Test
    fun existTest(){
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.searchTemplate).assertExists()
            onNode(SearchScreenTestObj.searchBar).assertExists()
            onNode(SearchScreenTestObj.mayLikeTemplate).assertExists()
            onNode(SearchScreenTestObj.seeAllMayLikeTemplate).assertExists()
            onNode(SearchScreenTestObj.mayLikeList).assertExists()
            onNode(SearchScreenTestObj.discoverSeriesTemplate).assertExists()
            onNode(SearchScreenTestObj.seeAllDiscoverTemplate).assertExists()
            onNode(SearchScreenTestObj.discoverList).assertExists()
            onNode(SearchScreenTestObj.charactersTemplate).assertExists()
            onNode(SearchScreenTestObj.seeAllCharactersTemplate).assertExists()
            onNode(SearchScreenTestObj.characterList).assertExists()
        }
    }




    @Test
    fun navigateToMayLikeTest() = runTest(){
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUp(secondSeriesExample)

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.mayLikeList).performClick()
            onNode(AboutSeriesScreenTestObj(secondSeriesExample).titleTemplate).assertExists()

        }
    }

    @Test
    fun navigateToAllMayLikeTest(){
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllMayLikeTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.mayLikeSeriesCard).assertExists()

        }
    }

    @Test
    fun navigateToDiscoverTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUp(seriesExample)

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()
            onNode(AboutSeriesScreenTestObj(seriesExample).titleTemplate).assertExists()

        }
    }

    @Test
    fun navigateToAllDiscoverTest(){
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllDiscoverTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.discoverSeriesCard).assertExists()

        }
    }

    @Test
    fun navigateToCharacterTest() = runTest{

        composeTestRule.run {
            setContent { MainScreen() }

            Mockito.`when`(
                remoteSeriesRepository.fetchSeries(listOf(1))
            ).thenReturn(emptyList())

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            Mockito.`when`(
                remoteCharacterRepository.getCharacterById(characterExample.characterId)
            ).thenReturn(characterExample)

            Mockito.`when`(
                remoteSeriesRepository.getCharacterSeries(characterExample.characterId)
            ).thenReturn(emptyList())

            onNode(SearchScreenTestObj.characterList).performClick()

            onNode(AboutCharacterScreenTestObj(characterExample).characterTemplate).assertExists()


        }

    }

    @Test
    fun navigateToAllCharacterTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }
            Mockito.`when`(
                remoteSeriesRepository.fetchSeries(listOf(1))
            ).thenReturn(emptyList())

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllCharactersTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.allCharacters).assertExists()

        }
    }

    @Test
    fun searchSeriesCharacterTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            Mockito.`when`(
                remoteCharacterRepository.getCharactersByName("Daredevil")
            ).thenReturn(listOf(characterExample))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(listOf(seriesExample))

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.searchEditText).assertExists()

            onNode(SearchScreenTestObj.searchEditText).performTextInput("Daredevil")
            onNode(SearchScreenTestObj.searchBar).performClick()

            onNode(SearchResultScreenTestObj.searchResultTemplate).assertExists()
            onNode(SearchResultScreenTestObj.resCharacterCard).assertExists()
            onNode(SearchResultScreenTestObj.resSeriesCard).assertExists()
        }

    }

    @Test
    fun searchSeriesTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            Mockito.`when`(
                remoteCharacterRepository.getCharactersByName("Daredevil")
            ).thenReturn(emptyList())

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(listOf(seriesExample))

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.searchEditText).assertExists()

            onNode(SearchScreenTestObj.searchEditText).performTextInput("Daredevil")
            onNode(SearchScreenTestObj.searchBar).performClick()

            onNode(SearchResultScreenTestObj.searchResultTemplate).assertExists()
            onNode(SearchResultScreenTestObj.resCharacterCard).assertDoesNotExist()
            onNode(SearchResultScreenTestObj.characterNotFoundMessage).assertExists()
            onNode(SearchResultScreenTestObj.resSeriesCard).assertExists()
        }
    }

    @Test
    fun errorSearchTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            Mockito.`when`(
                remoteCharacterRepository.getCharactersByName("")
            ).thenThrow(RuntimeException("Simulated error"))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("")
            ).thenThrow(RuntimeException("Simulated error"))

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.searchEditText).assertExists()

            onNode(SearchScreenTestObj.searchEditText).performTextInput("")
            onNode(SearchScreenTestObj.searchBar).performClick()

            onNode(SearchResultScreenTestObj.searchResultTemplate).assertExists()
            onNode(SearchResultScreenTestObj.resCharacterCard).assertDoesNotExist()
            onNode(SearchResultScreenTestObj.characterNotFoundMessage).assertDoesNotExist()
            onNode(SearchResultScreenTestObj.resSeriesCard).assertDoesNotExist()
            onNode(SearchResultScreenTestObj.updateButton).assertExists()
            onNode(SearchResultScreenTestObj.notFoundErrorText).assertExists()
        }
    }

    @Test
    fun searchResultCharacterNavigationTest() = runTest{

        composeTestRule.run {
            setContent { MainScreen() }

            Mockito.`when`(
                remoteCharacterRepository.getCharacterById(characterExample.characterId)
            ).thenReturn(characterExample)

            Mockito.`when`(
                remoteSeriesRepository.getCharacterSeries(characterExample.characterId)
            ).thenReturn(emptyList())

            Mockito.`when`(
                remoteCharacterRepository.getCharactersByName("Daredevil")
            ).thenReturn(listOf(characterExample))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(listOf(seriesExample))

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.searchEditText).assertExists()

            onNode(SearchScreenTestObj.searchEditText).performTextInput("Daredevil")
            onNode(SearchScreenTestObj.searchBar).performClick()

            onNode(SearchResultScreenTestObj.searchResultTemplate).assertExists()
            onNode(SearchResultScreenTestObj.resCharacterCard).performClick()

            onNode(AboutCharacterScreenTestObj(characterExample).characterTemplate).assertExists()
        }
    }

    @Test
    fun searchResultSeriesNavigationTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUp(seriesExample)

            Mockito.`when`(
                remoteCharacterRepository.getCharactersByName("Daredevil")
            ).thenReturn(listOf(characterExample))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(listOf(seriesExample))


            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.searchEditText).assertExists()

            onNode(SearchScreenTestObj.searchEditText).performTextInput("Daredevil")
            onNode(SearchScreenTestObj.searchBar).performClick()

            onNode(SearchResultScreenTestObj.searchResultTemplate).assertExists()
            onNode(SearchResultScreenTestObj.resSeriesCard).performClick()

            onNode(AboutSeriesScreenTestObj(seriesExample).titleTemplate).assertExists()

        }
    }


}