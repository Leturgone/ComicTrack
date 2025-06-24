package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.comictracker.di.DatabaseModule
import com.example.comictracker.di.NetworkModule
import com.example.comictracker.di.RepositoryModule
import com.example.comictracker.di.TestUseCasesModule
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class,NetworkModule::class,RepositoryModule::class,TestUseCasesModule::class)
class HomeScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()


    @Inject
    lateinit var remoteComicRepository: RemoteComicsRepository

    @Inject
    lateinit var localReadRepository: LocalReadRepository

    @Inject
    lateinit var remoteCharacterRepository:RemoteCharacterRepository

    @Inject
    lateinit var remoteCreatorsRepository: RemoteCreatorsRepository

    private lateinit var mockHelper: MockHelper

    @Before
    fun setUp() = runTest{
        hiltRule.inject()

        mockHelper = MockHelper(
            remoteComicsRepository = remoteComicRepository,
            remoteCharacterRepository = remoteCharacterRepository,
            remoteCreatorsRepository = remoteCreatorsRepository,
            localReadRepository = localReadRepository,
        )

        mockHelper.mockHomeScreenSetUp()
    }


    @Test
    fun existTest() = runTest {
        composeTestRule.run {

            setContent { MainScreen()}
            onNode(HomeScreenTestObj.newReleasesTemplate).assertExists()
            onNode(HomeScreenTestObj.newReleasesCard).assertExists()
            onNode(HomeScreenTestObj.continueReadingTemplate).assertExists()
            onNode(HomeScreenTestObj.continueReadingList).assertExists()

            onNode(HomeScreenTestObj.seeAllNewTemplate).assertExists()
            onNode(HomeScreenTestObj.seeAllContinueTemplate).assertExists()

            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
        }
    }

    @Test
    fun navigationToAllNewTest() = runTest {

        composeTestRule.run {
            setContent { MainScreen()}
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).assertExists()
        }

    }

    @Test
    fun navigationToNewTest() = runTest {

        composeTestRule.run {
            mockHelper.mockComicScreenSetup(comicExample,"unread")
            setContent { MainScreen()}
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).performClick()
            onNode(AboutComicScreenTestObj(comicExample).titleTemplate).assertExists()
        }
    }

    @Test
    fun navigationToNextTest() = runTest{

        composeTestRule.run {
            mockHelper.mockComicScreenSetup(secondComicExample,"unread")
            setContent { MainScreen()}
            onNode(HomeScreenTestObj.seeAllContinueTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.continueReadingCard).performClick()

            onNode(AboutComicScreenTestObj(secondComicExample).titleTemplate).assertExists()
        }
    }

    @Test
    fun navigationToAllNextTest() = runTest{

        composeTestRule.run {
            setContent { MainScreen()}
            onNode(HomeScreenTestObj.seeAllContinueTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.continueReadingCard).assertExists()
        }
    }

}
