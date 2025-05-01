package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.comictracker.di.AppModule
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
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
class HomeScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    val newReleasesMockList = listOf(
        comicExample
    )

    val continueReadingMockList = listOf(
        secondComicExample
    )

    @Inject
    lateinit var remoteComicRepository: RemoteComicsRepository

    @Inject
    lateinit var localReadRepository: LocalReadRepository

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun existTest() = runTest {

        Mockito.`when`(
            localReadRepository.loadCurrentReadIds(0)
        ).thenReturn(listOf(1, 2, 3))

        Mockito.`when`(
            localReadRepository.loadNextReadComicIds(0)
        ).thenReturn(listOf(1, 2, 3))

        Mockito.`when`(
            remoteComicRepository.fetchUpdatesForSeries(listOf(1, 2, 3))
        ).thenReturn(newReleasesMockList)

        Mockito.`when`(
            remoteComicRepository.fetchComics(listOf(1, 2, 3))
        ).thenReturn(continueReadingMockList)

        composeTestRule.run {
            setContent { MainScreen()}
            onNode(HomeScreen.newReleasesTemplate).assertExists()
            onNode(HomeScreen.newReleasesCard).assertExists()
            onNode(HomeScreen.continueReadingTemplate).assertExists()
            onNode(HomeScreen.continueReadingList).assertExists()

            onNode(HomeScreen.seeAllNewTemplate).assertExists()
            onNode(HomeScreen.seeAllContinueTemplate).assertExists()

            onNode(HomeScreen.seeAllNewTemplate).performClick()

            onNode(AllScreen.AllTemplate).assertExists()
        }
    }

    @Test
    fun navigationToAllNewTest() = runTest{
        Mockito.`when`(
            localReadRepository.loadCurrentReadIds(0)
        ).thenReturn(listOf(1, 2, 3))

        Mockito.`when`(
            localReadRepository.loadNextReadComicIds(0)
        ).thenReturn(listOf(1, 2, 3))

        Mockito.`when`(
            remoteComicRepository.fetchUpdatesForSeries(listOf(1, 2, 3))
        ).thenReturn(newReleasesMockList)

        Mockito.`when`(
            remoteComicRepository.fetchComics(listOf(1, 2, 3))
        ).thenReturn(continueReadingMockList)

        composeTestRule.run {
            setContent { MainScreen()}
            onNode(HomeScreen.seeAllNewTemplate).performClick()

            onNode(AllScreen.AllTemplate).assertExists()
        }

    }

    @Test
    fun navigationToNextTest(){}

    @Test
    fun navigationToNewTest(){}

    @Test
    fun navigationToAllNextTest(){}

}
