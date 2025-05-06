package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.comictracker.di.AppModule
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.local.LocalWriteRepository
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
class AllScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Inject
    lateinit var remoteComicRepository: RemoteComicsRepository

    @Inject
    lateinit var localReadRepository: LocalReadRepository

    @Inject
    lateinit var localWriteRepository: LocalWriteRepository

    @Inject
    lateinit var remoteCharacterRepository: RemoteCharacterRepository

    @Inject
    lateinit var remoteCreatorsRepository: RemoteCreatorsRepository

    @Inject
    lateinit var remoteSeriesRepository: RemoteSeriesRepository

    private lateinit var mockHelper:MockHelper

    @Before
    fun setUp() = runTest {
        hiltRule.inject()

        mockHelper = MockHelper(
            remoteSeriesRepository = remoteSeriesRepository,
            remoteComicsRepository = remoteComicRepository,
            remoteCharacterRepository = remoteCharacterRepository,
            remoteCreatorsRepository = remoteCreatorsRepository,
            localReadRepository = localReadRepository,
            localWriteRepository = localWriteRepository
        )

        //HomeScreen
        mockHelper.mockHomeScreenSetUp()


    }

    @Test
    fun existTestAllComic(){
        composeTestRule.run {
            setContent { MainScreen() }
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).assertExists()
            onNode(AllScreenTestObj.nextButton).assertExists()
        }
    }

    @Test
    fun existTestAllSeries() = runTest{
        composeTestRule.run {

            //SearchScreen
            mockHelper.mockSearchScreenSetup()

            setContent { MainScreen() }
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllDiscoverTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.discoverSeriesCard).assertExists()
            onNode(AllScreenTestObj.nextButton).assertExists()
        }
    }

    @Test
    fun existTestAllCharacters() = runTest{
        composeTestRule.run {

            //SearchScreen
            mockHelper.mockSearchScreenSetup()

            Mockito.`when`(
                remoteSeriesRepository.getAllSeries()
            ).thenReturn(emptyList())

            setContent { MainScreen() }
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllCharactersTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.allCharacters).assertExists()
            onNode(AllScreenTestObj.nextButton).assertExists()
        }
    }

    @Test
    fun navigateToNextPageTest() = runTest(){
        composeTestRule.run {
            setContent { MainScreen() }
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            Mockito.`when`(
                localReadRepository.loadCurrentReadIds(9)
            ).thenReturn(listOf(455))

            Mockito.`when`(
                remoteComicRepository.fetchUpdatesForSeries(listOf(455))
            ).thenReturn(listOf(secondComicExample))

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).assertExists()
            onNode(AllScreenTestObj.nextButton).performClick()
            onNode(AllScreenTestObj.newReleasesNextButtonCard).assertExists()
        }
    }

    @Test
    fun navigateToPreviousPageTest() = runTest(){
        composeTestRule.run {
            setContent { MainScreen() }
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            Mockito.`when`(
                localReadRepository.loadCurrentReadIds(9)
            ).thenReturn(listOf(455))

            Mockito.`when`(
                remoteComicRepository.fetchUpdatesForSeries(listOf(455))
            ).thenReturn(listOf(secondComicExample))

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).assertExists()
            onNode(AllScreenTestObj.nextButton).performClick()
            onNode(AllScreenTestObj.newReleasesNextButtonCard).assertExists()

            onNode(AllScreenTestObj.backButton).performClick()
            onNode(AllScreenTestObj.newReleasesCard).assertExists()
        }

    }

    @Test
    fun allSeriesScreenNavigateTest() =  runTest{
        composeTestRule.run {

            //SearchScreen
            mockHelper.mockSearchScreenSetup()
            mockHelper.mockSeriesSetUp(seriesExample)

            setContent { MainScreen() }
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllDiscoverTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.discoverSeriesCard).performClick()

            onNode(AboutSeriesScreenTestObj(seriesExample).titleTemplate).assertExists()
        }
    }

    @Test
    fun allComicsScreenNavigateTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockComicScreenSetup(comicExample,"read")
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).performClick()

            onNode(AboutComicScreenTestObj(comicExample).titleTemplate).assertExists()


        }
    }

    @Test
    fun allCharactersScreenNavigateTest() = runTest{
        composeTestRule.run {

            //SearchScreen
            mockHelper.mockSearchScreenSetup()


            Mockito.`when`(
                remoteSeriesRepository.getAllSeries()
            ).thenReturn(emptyList())

            mockHelper.mockCharacterScreen(characterExample, emptyList())

            setContent { MainScreen() }
            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.seeAllCharactersTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.allCharacters).performClick()

            onNode(AboutCharacterScreenTestObj(characterExample).characterTemplate).assertExists()
            onNode(AboutCharacterScreenTestObj(characterExample).descTemplate).assertExists()
        }
    }
}