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
import org.mockito.kotlin.any
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class ComicScreenTests {
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
    fun existTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            mockHelper.mockComicScreenSetup(comicExample,"read")

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            onNode(comicScreenNode.titleTemplate).assertExists()
            onNode(comicScreenNode.seriesTemplate).assertExists()
            onNode(comicScreenNode.seriesTitleTemplate).assertExists()
            onNode(comicScreenNode.dateTemplate).assertExists()
            onNode(comicScreenNode.comicDateTemplate).assertExists()
            onNode(comicScreenNode.markUnreadTemplate).assertExists()
            onNode(comicScreenNode.creatorsTemplate).assertExists()
            onNode(comicScreenNode.creatorsList).assertExists()
            onNode(comicScreenNode.charactersTemplate).assertExists()
            onNode(comicScreenNode.charactersList).assertExists()

        }
    }

    @Test
    fun navigateToSeriesTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            mockHelper.mockComicScreenSetup(comicExample,"read")

            mockHelper.mockSeriesSetUp(seriesExample)

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            onNode(comicScreenNode.titleTemplate).assertExists()
            onNode(comicScreenNode.seriesTemplate).assertExists()
            onNode(comicScreenNode.seriesTitleTemplate).performClick()

            onNode(AboutSeriesScreenTestObj(seriesExample).titleTemplate).assertExists()
            onNode(AboutSeriesScreenTestObj(seriesExample).unreadTemplate).assertExists()

        }
    }

    @Test
    fun navigateToCharacterTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            mockHelper.mockComicScreenSetup(comicExample,"read")

            Mockito.`when`(
                remoteCharacterRepository.getCharacterById(characterExample.characterId)
            ).thenReturn(characterExample)

            Mockito.`when`(
                remoteSeriesRepository.getCharacterSeries(characterExample.characterId)
            ).thenReturn(emptyList())

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            val characterScreenNode = AboutCharacterScreenTestObj(characterExample)

            onNode(comicScreenNode.titleTemplate).assertExists()
            onNode(comicScreenNode.charactersList).assertExists()
            onNode(comicScreenNode.charactersList).performClick()

            onNode(characterScreenNode.characterTemplate).assertExists()
            onNode(characterScreenNode.descTemplate).assertExists()
        }
    }

    @Test
    fun markAsReadTest() = runTest{
        composeTestRule.run{
            setContent{ MainScreen()}

            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            mockHelper.mockComicScreenSetup(comicExample,"unread")

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            onNode(comicScreenNode.markReadTemplates).assertExists()


            Mockito.`when`(
                remoteComicRepository.getNextComicId(comicExample.seriesId, 1)
            ).thenReturn(null)

            Mockito.`when`(
                localWriteRepository.markComicRead(comicExample.comicId, comicExample.seriesId,null)
            ).thenReturn(true)

            mockHelper.mockComicScreenSetup(comicExample,"read")
            onNode(comicScreenNode.markReadTemplates).performClick()

            onNode(comicScreenNode.markUnreadTemplate).assertExists()
        }

    }

    @Test
    fun markAsUnReadTest() = runTest{
        composeTestRule.run{
            setContent{ MainScreen()}

            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            mockHelper.mockComicScreenSetup(comicExample,"read")

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            onNode(comicScreenNode.markUnreadTemplate).assertExists()


            Mockito.`when`(
                remoteComicRepository.getPreviousComicId(comicExample.seriesId, 1)
            ).thenReturn(null)

            Mockito.`when`(
                localWriteRepository.markComicUnread(comicExample.comicId, comicExample.seriesId,null)
            ).thenReturn(true)

            mockHelper.mockComicScreenSetup(comicExample,"unread")
            onNode(comicScreenNode.markUnreadTemplate).performClick()

            onNode(comicScreenNode.markReadTemplates).assertExists()
        }
    }

    @Test
    fun errorWhileMarkTest()= runTest{
        composeTestRule.run{
            setContent{ MainScreen()}

            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            mockHelper.mockComicScreenSetup(comicExample,"read")

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            onNode(comicScreenNode.markUnreadTemplate).assertExists()


            Mockito.`when`(
                remoteComicRepository.getPreviousComicId(comicExample.seriesId, 1)
            ).thenReturn(null)

            Mockito.`when`(
                localWriteRepository.markComicUnread(comicExample.comicId, comicExample.seriesId,null)
            ).thenReturn(false)

            onNode(comicScreenNode.markUnreadTemplate).performClick()

            onNode(comicScreenNode.markUnreadTemplate).assertExists()
        }
    }

}