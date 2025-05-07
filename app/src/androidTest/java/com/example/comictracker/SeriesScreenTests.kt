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
class SeriesScreenTests {
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
        mockHelper.mockHomeScreenSetUp()
        mockHelper.mockSearchScreenSetup()

    }


    @Test
    fun existTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample,"unread",false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.titleTemplate).assertExists()
            onNode(aboutSeriesNode.dateTemplate).assertExists()
            onNode(aboutSeriesNode.seriesDateTemplate).assertExists()
            onNode(aboutSeriesNode.descTemplate).assertExists()
            onNode(aboutSeriesNode.seriesDescTemplate).assertExists()
            onNode(aboutSeriesNode.seeMoreTemplate).assertExists()
            onNode(aboutSeriesNode.seeLessTemplate).assertDoesNotExist()

            onNode(aboutSeriesNode.unreadTemplate).assertExists()
            onNode(aboutSeriesNode.favoriteMark).assertExists()

            onNode(aboutSeriesNode.continueReadingTemplate).assertExists()

            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.nextReadItem).assertExists()
            onNode(aboutSeriesNode.readItemMark).assertExists()

            onNode(aboutSeriesNode.creatorsTemplate).assertExists()
            onNode(aboutSeriesNode.creatorsList).assertExists()
            onNode(aboutSeriesNode.charactersTemplate).assertExists()
            onNode(aboutSeriesNode.charactersList).assertExists()
            onNode(aboutSeriesNode.connectedTemplate).assertExists()
            onNode(aboutSeriesNode.connectedList).assertExists()

        }
    }

    @Test
    fun markAsReadTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "unread", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.unreadTemplate).performClick()

            onNode(aboutSeriesNode.bottomSheetReadMark).assertExists()
            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            Mockito.`when`(
                localWriteRepository.markSeriesRead(seriesExample.seriesId)
            ).thenReturn(true)

            onNode(aboutSeriesNode.bottomSheetTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetReadMark).performClick()

            onNode(aboutSeriesNode.readTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetTemplate).assertDoesNotExist()

        }

    }

    @Test
    fun markAsUnreadTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.readTemplate).performClick()

            onNode(aboutSeriesNode.bottomSheetReadMark).assertExists()
            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "unread", false, comicExample)

            Mockito.`when`(
                localWriteRepository.markSeriesUnread(seriesExample.seriesId)
            ).thenReturn(true)

            onNode(aboutSeriesNode.bottomSheetTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetUnreadMark).performClick()

            onNode(aboutSeriesNode.unreadTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetTemplate).assertDoesNotExist()

        }
    }

    @Test
    fun markAsWillBeReadTest(){}

    @Test
    fun markAsCurrentlyReadingTest(){}

    @Test
    fun markAsFavoriteTest(){}

    @Test
    fun unmarkAsFavoriteTest(){}

    @Test
    fun markNextComicAsReadTest(){}

    @Test
    fun navigateToAllComicFromSeriesTest(){}

    @Test
    fun markAsReadComicFromSeriesTest(){}

    @Test
    fun markAsUnreadComicFromSeriesTest(){}

    @Test
    fun navigateToCharactersTest(){}

    @Test
    fun navigateToConnectedSeriesTest(){}

    @Test
    fun expTestTest(){}


}