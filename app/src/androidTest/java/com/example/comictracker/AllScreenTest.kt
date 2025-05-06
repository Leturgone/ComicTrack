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
    fun navigateToNextPageTest(){

    }

    @Test
    fun navigateToPreviousPageTest(){

    }

    @Test
    fun allSeriesScreenNavigateTest(){

    }

    @Test
    fun allComicsScreenNavigateTest(){

    }

    @Test
    fun allCharactersScreenNavigateTest(){

    }
}