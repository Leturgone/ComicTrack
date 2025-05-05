package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.comictracker.di.AppModule
import com.example.comictracker.domain.model.StatisticsforAll
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
import org.mockito.Mock
import org.mockito.Mockito
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class LibraryScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

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

        //HomeScreen
        mockHelper.mockHomeScreenSetUp()

        Mockito.`when`(
            localReadRepository.loadStatistics()
        ).thenReturn(
            StatisticsforAll(1,2,3,4,5)
        )


        Mockito.`when`(
            localReadRepository.loadFavoritesIds()
        ).thenReturn(listOf(1))

        Mockito.`when`(
            localReadRepository.loadHistory(0)
        ).thenReturn(listOf(25))

        Mockito.`when`(
            remoteSeriesRepository.fetchSeries(listOf(1))
        ).thenReturn(listOf(secondSeriesExample))

        Mockito.`when`(
            remoteSeriesRepository.fetchSeries(listOf(1,2,3))
        ).thenReturn(listOf(seriesExample))

        Mockito.`when`(
            remoteComicRepository.fetchComics(listOf(25))
        ).thenReturn(listOf(comicExample))

    }

    @Test
    fun existTest(){
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(BottomBarTestObj.libraryTemplate).assertExists()
            onNode(BottomBarTestObj.libraryTemplate).performClick()

            onNode(LibraryScreenTestObj.libraryTemplate).assertExists()
            onNode(LibraryScreenTestObj.comicsTemplate).assertExists()
            onNode(LibraryScreenTestObj.seriesTemplate).assertExists()
            onNode(LibraryScreenTestObj.readlistTemplate).assertExists()
            onNode(LibraryScreenTestObj.favoriteTemplate).assertExists()
            onNode(LibraryScreenTestObj.favoritesList).assertExists()
            onNode(LibraryScreenTestObj.curReadTemplate).assertExists()
            onNode(LibraryScreenTestObj.seeAllCurrentTemplate).assertExists()
            onNode(LibraryScreenTestObj.curReadList).assertExists()
            onNode(LibraryScreenTestObj.lastUpdatesTemplate).assertExists()
            onNode(LibraryScreenTestObj.seeAllLastTemplate).assertExists()
            onNode(LibraryScreenTestObj.lastUpdatesList).assertExists()

        }
    }

    @Test
    fun navigateToAllComicsTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }


            Mockito.`when`(
                localReadRepository.loadAllReadComicIds(0)
            ).thenReturn(listOf(6))

            Mockito.`when`(
                remoteComicRepository.fetchComics(listOf(6))
            ).thenReturn(listOf(secondComicExample))

            onNode(BottomBarTestObj.libraryTemplate).assertExists()
            onNode(BottomBarTestObj.libraryTemplate).performClick()

            onNode(LibraryScreenTestObj.comicsTemplate).performClick()
            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.allComicCard).assertExists()
        }
    }

    @Test
    fun navigateToAllSeriesTest()= runTest{
        composeTestRule.run {
            setContent { MainScreen() }


            Mockito.`when`(
                localReadRepository.loadAllReadSeriesIds(0)
            ).thenReturn(listOf(5))

            Mockito.`when`(
                remoteSeriesRepository.fetchSeries(listOf(5))
            ).thenReturn(listOf(secondSeriesExample))

            onNode(BottomBarTestObj.libraryTemplate).assertExists()
            onNode(BottomBarTestObj.libraryTemplate).performClick()

            onNode(LibraryScreenTestObj.seriesTemplate).performClick()
            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.allSeriesCard).assertExists()
        }
    }

    @Test
    fun navigateToReadlistTest()= runTest{
        composeTestRule.run {
            setContent { MainScreen() }


            Mockito.`when`(
                localReadRepository.loadWillBeReadIds(0)
            ).thenReturn(listOf(7))

            Mockito.`when`(
                remoteSeriesRepository.fetchSeries(listOf(7))
            ).thenReturn(listOf(seriesExample))

            onNode(BottomBarTestObj.libraryTemplate).assertExists()
            onNode(BottomBarTestObj.libraryTemplate).performClick()

            onNode(LibraryScreenTestObj.readlistTemplate).performClick()
            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.allReadlistCard).assertExists()
        }
    }

    @Test
    fun navigateToFavorites() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUp(secondSeriesExample)

            onNode(BottomBarTestObj.libraryTemplate).assertExists()
            onNode(BottomBarTestObj.libraryTemplate).performClick()

            onNode(LibraryScreenTestObj.favoritesList).performClick()
            onNode(AboutSeriesScreenTestObj(secondSeriesExample).titleTemplate).assertExists()
            onNode(AboutSeriesScreenTestObj(secondSeriesExample).unreadTemplate).assertExists()
        }
    }

    @Test
    fun navigateToCurrentlyReading(){}

    @Test
    fun navigateToAllCurrentlyReading(){}

    @Test
    fun navigateToLastUpdates(){}

    @Test
    fun navigateToAllLastUpdates(){}






}