package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.comictracker.data.database.enteties.SeriesEntity
import com.example.comictracker.di.AppModule
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
@UninstallModules(AppModule::class)
class SearchScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private val mayLikeSeriesList = listOf(secondSeriesExample)

    private val discoverSeriesList = listOf(seriesExample)

    private val characterList = listOf(characterExample)

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

    @Before
    fun setUp() = runTest{
        hiltRule.inject()

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
        ).thenReturn(listOf(characterExample))
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
    fun navigateToMayLikeTest(){
        composeTestRule.run {
            setContent { MainScreen() }

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
    fun navigateToDiscoverTest(){
        composeTestRule.run {  }
    }

    @Test
    fun navigateToAllDiscoverTest(){}

    @Test
    fun navigateToCharacterTest(){}

    @Test
    fun navigateToAllCharacterTest(){}

    @Test
    fun searchSeriesCharacterTest(){}

    @Test
    fun searchSeriesTest(){}

    @Test
    fun errorSearchTest(){}


}