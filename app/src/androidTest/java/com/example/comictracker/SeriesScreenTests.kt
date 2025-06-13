package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
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
            onNode(aboutSeriesNode.unFavoriteMark).assertExists()

            onNode(aboutSeriesNode.continueReadingTemplate).assertExists()

            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.nextReadItem).assertExists()
            onNode(aboutSeriesNode.unreadItemMark).assertExists()

            onNode(aboutSeriesNode.creatorsTemplate).assertExists()
            onNode(aboutSeriesNode.creatorsList).assertExists()
            onNode(aboutSeriesNode.seriesScreenScroll).performTouchInput {
                swipeUp()
            }
            onNode(aboutSeriesNode.charactersTemplate).assertExists()
            onNode(aboutSeriesNode.charactersList).assertExists()
            onNode(aboutSeriesNode.connectedTemplate).assertExists()
            onNode(aboutSeriesNode.connectedList).assertExists()

        }
    }

    @Test
    fun navigateToComicTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "unread", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            mockHelper.mockComicScreenSetup(comicExample,"unread")
            onNode(aboutSeriesNode.nextReadItem).assertExists()

            onNode(aboutSeriesNode.nextReadItem).performClick()

            onNode(AboutComicScreenTestObj(comicExample).titleTemplate).assertExists()
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
    fun markAsWillBeReadTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.readTemplate).performClick()

            onNode(aboutSeriesNode.bottomSheetReadMark).assertExists()
            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "will", false, comicExample)

            Mockito.`when`(
                localWriteRepository.addSeriesToWillBeRead(seriesExample.seriesId)
            ).thenReturn(true)

            onNode(aboutSeriesNode.bottomSheetTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetWillBeReadMark).performClick()

            onNode(aboutSeriesNode.willBeReadTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetTemplate).assertDoesNotExist()
        }
    }

    @Test
    fun markAsCurrentlyReadingTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.readTemplate).performClick()

            onNode(aboutSeriesNode.bottomSheetReadMark).assertExists()
            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "currently", false, comicExample)

            Mockito.`when`(
                localWriteRepository.addSeriesToCurrentlyRead(seriesExample.seriesId, comicExample.comicId)
            ).thenReturn(true)

            onNode(aboutSeriesNode.bottomSheetTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetCurrentlyMark).performClick()

            onNode(aboutSeriesNode.currentlyReadingTemplate).assertExists()
            onNode(aboutSeriesNode.bottomSheetTemplate).assertDoesNotExist()
        }
    }

    @Test
    fun markAsFavoriteTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)
            Mockito.`when`(
                localWriteRepository.addSeriesToFavorite(seriesExample.seriesId)
            ).thenReturn(true)


            onNode(aboutSeriesNode.favoriteMark).assertDoesNotExist()


            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", true, comicExample)

            onNode(aboutSeriesNode.unFavoriteMark).performClick()

            onNode(aboutSeriesNode.favoriteMark).assertExists()

        }
    }

    @Test
    fun unMarkAsFavoriteTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", true, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)
            Mockito.`when`(
                localWriteRepository.removeSeriesFromFavorite(seriesExample.seriesId)
            ).thenReturn(true)


            onNode(aboutSeriesNode.unFavoriteMark).assertDoesNotExist()


            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(aboutSeriesNode.favoriteMark).performClick()

            onNode(aboutSeriesNode.unFavoriteMark).assertExists()

        }
    }

    @Test
    fun markNextComicAsReadTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", true, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.nextReadItem).assertExists()

            Mockito.`when`(
                remoteComicRepository.getNextComicId(
                    comicExample.seriesId,
                    comicExample.number.toFloat().toInt())
            ).thenReturn(Result.success(secondComicExample.comicId))

            Mockito.`when`(
                localWriteRepository.markComicRead(
                    comicExample.comicId,
                    comicExample.seriesId,
                    secondComicExample.comicId)
            ).thenReturn(true)



            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", true, secondComicExample)
            onNode(aboutSeriesNode.unreadItemMark).assertExists()
            onNode(aboutSeriesNode.unreadItemMark).performClick()
            onNode(aboutSeriesNode.secondNestReadItem).assertExists()

        }

    }

    @Test
    fun markLastComicAsUnReadTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", true, secondComicExample.copy(readMark = "read"))

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            onNode(aboutSeriesNode.secondNestReadItem).assertExists()


            Mockito.`when`(
                remoteComicRepository.getPreviousComicId(
                    secondComicExample.seriesId,
                    secondComicExample.number.toFloat().toInt())
            ).thenReturn(Result.success(comicExample.comicId))

            Mockito.`when`(
                localWriteRepository.markComicUnread(
                    secondComicExample.comicId,
                    seriesExample.seriesId,
                    comicExample.comicId)
            ).thenReturn(true)

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", true, comicExample)

            onNode(aboutSeriesNode.readItemMark).assertExists()
            onNode(aboutSeriesNode.readItemMark).performClick()
            onNode(aboutSeriesNode.nextReadItem).assertExists()

        }

    }

    @Test
    fun navigateToAllComicFromSeriesTest() = runTest {
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            mockHelper.mockAllComicsFromSeriesScreenSetup(seriesExample, listOf(comicExample,
                secondComicExample))

            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.seeAllTemplate).performClick()

            onNode(AllComicFromSeriesScreenTestObj.AllTemplate).assertExists()

            onNode(AllComicFromSeriesScreenTestObj.comicListItem).assertExists()
        }
    }

    @Test
    fun markAsReadComicFromSeriesTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            mockHelper.mockAllComicsFromSeriesScreenSetup(
                seriesExample, listOf(
                    secondComicExample
                ),"unread"
            )

            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.seeAllTemplate).performClick()

            onNode(AllComicFromSeriesScreenTestObj.comicListItem).assertExists()

            Mockito.`when`(
                remoteComicRepository.getNextComicId(
                    secondComicExample.seriesId,
                    secondComicExample.number.toFloat().toInt())
            ).thenReturn(null)

            Mockito.`when`(
                localWriteRepository.markComicRead(
                    secondComicExample.comicId,
                    secondComicExample.seriesId,
                    null)
            ).thenReturn(true)


            mockHelper.mockAllComicsFromSeriesScreenSetup(
                seriesExample, listOf(
                    secondComicExample
                ),"read"
            )

            onNode(AllComicFromSeriesScreenTestObj.unreadItemMark).assertExists()

            onNode(AllComicFromSeriesScreenTestObj.unreadItemMark).performClick()


            onNode(AllComicFromSeriesScreenTestObj.readItemMark).assertExists()
        }
    }

    @Test
    fun markAsUnreadComicFromSeriesTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            mockHelper.mockAllComicsFromSeriesScreenSetup(
                seriesExample, listOf(
                    secondComicExample
                ),"read"
            )

            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.seeAllTemplate).performClick()

            onNode(AllComicFromSeriesScreenTestObj.comicListItem).assertExists()

            Mockito.`when`(
                remoteComicRepository.getPreviousComicId(
                    secondComicExample.seriesId,
                    secondComicExample.number.toFloat().toInt())
            ).thenReturn(null)

            Mockito.`when`(
                localWriteRepository.markComicUnread(
                    secondComicExample.comicId,
                    seriesExample.seriesId,
                    null)
            ).thenReturn(true)


            mockHelper.mockAllComicsFromSeriesScreenSetup(
                seriesExample, listOf(
                    secondComicExample
                ),"unread"
            )

            onNode(AllComicFromSeriesScreenTestObj.readItemMark).assertExists()

            onNode(AllComicFromSeriesScreenTestObj.readItemMark).performClick()


            onNode(AllComicFromSeriesScreenTestObj.unreadItemMark).assertExists()
        }
    }

    @Test
    fun navigateToComicFromAllComicsTest() = runTest {
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            mockHelper.mockAllComicsFromSeriesScreenSetup(seriesExample, listOf(comicExample,
                secondComicExample))

            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.seeAllTemplate).performClick()

            onNode(AllComicFromSeriesScreenTestObj.comicListItem).assertExists()

            mockHelper.mockComicScreenSetup(secondComicExample,"read")

            onNode(AllComicFromSeriesScreenTestObj.comicListItem).performClick()

            onNode(AboutComicScreenTestObj(secondComicExample).titleTemplate).assertExists()
        }
    }

    @Test
    fun loadMoreComicsInListTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)

            mockHelper.mockAllComicsFromSeriesScreenSetup(seriesExample,
                listOf(
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    secondComicExample,secondComicExample,secondComicExample,secondComicExample,secondComicExample,
                    )
            )
            Mockito.`when`(
                remoteComicRepository.getComicsFromSeries(seriesExample.seriesId,50)
            ).thenReturn(
                Result.success(listOf(comicExample))
            )
            onNode(aboutSeriesNode.seeAllTemplate).assertExists()
            onNode(aboutSeriesNode.seeAllTemplate).performClick()


            repeat(3) {
                onNode(AllComicFromSeriesScreenTestObj.comicList).performTouchInput {
                    swipeUp()
                }
            }

            onNode(AllComicFromSeriesScreenTestObj.loadMoreButton).assertExists()
            onNode(AllComicFromSeriesScreenTestObj.loadMoreButton).performClick()

            mockHelper.mockAllComicsFromSeriesScreenSetup(seriesExample, listOf(comicExample))
            onNode(AllComicFromSeriesScreenTestObj.AllTemplate).assertExists()
            onNode(AllComicFromSeriesScreenTestObj.secondPageItem).assertExists()
            onNode(AllComicFromSeriesScreenTestObj.comicListItem).assertDoesNotExist()
        }
    }

    @Test
    fun navigateToCharactersTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)

            mockHelper.mockCharacterScreen(characterExample, listOf(seriesExample))

            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)


            onNode(aboutSeriesNode.seriesScreenScroll).performTouchInput {
                swipeUp()
            }

            onNode(aboutSeriesNode.charactersTemplate).assertExists()
            onNode(aboutSeriesNode.charactersList).assertExists()
            onNode(aboutSeriesNode.charactersList).performClick()

            onNode(AboutCharacterScreenTestObj(characterExample).characterTemplate).assertExists()
            onNode(AboutCharacterScreenTestObj(characterExample).characterDesc).assertExists()
        }

    }


    @Test
    fun navigateToConnectedSeriesTest() = runTest {
        composeTestRule.run {
            setContent { MainScreen() }

            mockHelper.mockSeriesSetUpForSeriesTest(seriesExample, "read", false, comicExample)



            onNode(BottomBarTestObj.searchTemplate).performClick()

            onNode(SearchScreenTestObj.discoverList).performClick()

            val aboutSeriesNode = AboutSeriesScreenTestObj(seriesExample)


            onNode(aboutSeriesNode.seriesScreenScroll).performTouchInput {
                swipeUp()
            }

            onNode(aboutSeriesNode.connectedTemplate).assertExists()
            onNode(aboutSeriesNode.connectedList).assertExists()

            mockHelper.mockSeriesSetUp(secondSeriesExample)

            onNode(aboutSeriesNode.connectedList).performClick()

            onNode(AboutSeriesScreenTestObj(secondSeriesExample).titleTemplate).assertExists()
        }

    }




}