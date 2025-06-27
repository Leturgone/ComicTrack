package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.comictracker.di.DatabaseModule
import com.example.comictracker.di.NetworkModule
import com.example.comictracker.di.RepositoryModule
import com.example.comictracker.di.TestUseCasesModule
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(
    DatabaseModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    TestUseCasesModule::class)
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
        ).thenReturn(Result.success(discoverSeriesList))

        //MayLike list mock
        Mockito.`when`(
            localReadRepository.loadAllReadSeriesIds(0)
        ).thenReturn(Result.success(listOf(1,2,3)))

        Mockito.`when`(
            remoteSeriesRepository.loadMayLikeSeriesIds(listOf(1,2,3))
        ).thenReturn(Result.success(listOf(1)))


        Mockito.`when`(
            remoteSeriesRepository.fetchSeries(listOf(1))
        ).thenReturn(Result.success(mayLikeSeriesList))

        //Character list mock
        Mockito.`when`(
            remoteCharacterRepository.getAllCharacters()
        ).thenReturn(Result.success(characterList))
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
    fun navigateToHomeScreenTest(){
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(BottomBarTestObj.homeTemplate).performClick()

            onNode(HomeScreenTestObj.continueReadingTemplate).assertExists()
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
            ).thenReturn(Result.success(emptyList()))

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()

            Mockito.`when`(
                remoteCharacterRepository.getCharacterById(characterExample.characterId)
            ).thenReturn(Result.success(characterExample))

            Mockito.`when`(
                remoteSeriesRepository.getCharacterSeries(characterExample.characterId)
            ).thenReturn(Result.success(emptyList()))

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
            ).thenReturn(Result.success(emptyList()))

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
            ).thenReturn(Result.success(listOf(characterExample)))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(Result.success(listOf(seriesExample)))

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
            ).thenReturn(Result.success(emptyList()))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(Result.success(listOf(seriesExample)))

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
            ).thenReturn(Result.failure(Exception("Simulated error")))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("")
            ).thenReturn(Result.failure(Exception("Simulated error")))

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
            ).thenReturn(Result.success(characterExample))

            Mockito.`when`(
                remoteSeriesRepository.getCharacterSeries(characterExample.characterId)
            ).thenReturn(Result.success(emptyList()))

            Mockito.`when`(
                remoteCharacterRepository.getCharactersByName("Daredevil")
            ).thenReturn(Result.success(listOf(characterExample)))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(Result.success(listOf(seriesExample)))

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
            ).thenReturn(Result.success(listOf(characterExample)))

            Mockito.`when`(
                remoteSeriesRepository.getSeriesByTitle("Daredevil")
            ).thenReturn(Result.success(listOf(seriesExample)))


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

    @Test
    fun discoverListErrorTest() = runTest {

        //Discover list mock
        Mockito.`when`(
            remoteSeriesRepository.getAllSeries()
        ).thenReturn(Result.failure(Exception()))

        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()
            onNode(SearchScreenTestObj.discoverSeriesError).assertExists()
        }
    }
    @Test
    fun mayLikeListErrorTest() = runTest {

        Mockito.`when`(
            remoteSeriesRepository.loadMayLikeSeriesIds(listOf(1,2,3))
        ).thenReturn(Result.failure(Exception()))

        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()
            onNode(SearchScreenTestObj.mayLikeSeriesError).assertExists()
        }
    }

    @Test
    fun characterErrorTest() = runTest {

        //Character list mock
        Mockito.`when`(
            remoteCharacterRepository.getAllCharacters()
        ).thenReturn(Result.failure(Exception()))

        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.searchTemplate).assertExists()
            onNode(BottomBarTestObj.searchTemplate).performClick()
            onNode(SearchScreenTestObj.characterListError).assertExists()
        }
    }
}