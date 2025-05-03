package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.comictracker.di.AppModule
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
import org.mockito.Mockito
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class HomeScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private val newReleasesMockList = listOf(
        comicExample
    )

    private val continueReadingMockList = listOf(
        secondComicExample
    )

    @Inject
    lateinit var remoteComicRepository: RemoteComicsRepository

    @Inject
    lateinit var localReadRepository: LocalReadRepository

    @Inject
    lateinit var remoteCharacterRepository:RemoteCharacterRepository

    @Inject
    lateinit var remoteCreatorsRepository: RemoteCreatorsRepository

    @Before
    fun setUp() = runTest{
        hiltRule.inject()

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
        Mockito.`when`(
            remoteComicRepository.getComicById(4372)
        ).thenReturn(comicExample)


        Mockito.`when`(
            remoteCharacterRepository.getComicCharacters(4372)
        ).thenReturn(listOf(characterExample))

        Mockito.`when`(
            remoteCreatorsRepository.getComicCreators(comicExample.creators)
        ).thenReturn(listOf(creatorExample))

        Mockito.`when`(
            remoteCreatorsRepository.getComicCreators(comicExample.creators)
        ).thenReturn(listOf(creatorExample))

        Mockito.`when`(
            localReadRepository.loadComicMark(4372)
        ).thenReturn("unread")


        composeTestRule.run {
            setContent { MainScreen()}
            onNode(HomeScreenTestObj.seeAllNewTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(AllScreenTestObj.newReleasesCard).performClick()
            onNode(AboutComicScreenTestObj(comicExample).titleTemplate).assertExists()
        }
    }

    @Test
    fun navigationToNextTest() = runTest{

        Mockito.`when`(
            remoteComicRepository.getComicById(9450)
        ).thenReturn(secondComicExample)

        Mockito.`when`(
            remoteCharacterRepository.getComicCharacters(9450)
        ).thenReturn(listOf(characterExample))

        Mockito.`when`(
            remoteCreatorsRepository.getComicCreators(secondComicExample.creators)
        ).thenReturn(listOf(creatorExample))

        Mockito.`when`(
            remoteCreatorsRepository.getComicCreators(secondComicExample.creators)
        ).thenReturn(listOf(creatorExample))

        Mockito.`when`(
            localReadRepository.loadComicMark(9450)
        ).thenReturn("unread")

        composeTestRule.run {
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
