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
class CharacterUiTest {

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

        mockHelper.mockComicScreenSetup(comicExample,"read")

        Mockito.`when`(
            remoteCharacterRepository.getCharacterById(characterExample.characterId)
        ).thenReturn(Result.success(characterExample))

        Mockito.`when`(
            remoteSeriesRepository.getCharacterSeries(characterExample.characterId)
        ).thenReturn(Result.success(listOf(seriesExample)))
    }

    @Test
    fun existTest() = runTest{
        composeTestRule.run {

            setContent { MainScreen() }
            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            val characterScreenNode = AboutCharacterScreenTestObj(characterExample)

            onNode(comicScreenNode.titleTemplate).assertExists()
            onNode(comicScreenNode.charactersList).assertExists()
            onNode(comicScreenNode.charactersList).performClick()

            onNode(characterScreenNode.characterTemplate).assertExists()
            onNode(characterScreenNode.descTemplate).assertExists()
            onNode(characterScreenNode.characterDesc).assertExists()
            onNode(characterScreenNode.allTemplate).assertExists()
            onNode(characterScreenNode.seeAllTemplate).assertExists()
            onNode(characterScreenNode.characterSeriesCard).assertExists()
        }
    }

    @Test
    fun navigateToCharacterSeriesTest() = runTest{
        composeTestRule.run {
            setContent { MainScreen() }
            mockHelper.mockSeriesSetUp(seriesExample)
            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            val characterScreenNode = AboutCharacterScreenTestObj(characterExample)

            onNode(comicScreenNode.charactersList).performClick()

            onNode(characterScreenNode.characterSeriesCard).performClick()

            onNode(AboutSeriesScreenTestObj(seriesExample).titleTemplate).assertExists()
        }

    }

    @Test
    fun navigateToAllCharacterSeries(){
        composeTestRule.run {
            setContent { MainScreen() }
            onNode(HomeScreenTestObj.newReleasesCard).performClick()

            val comicScreenNode =AboutComicScreenTestObj(comicExample)

            val characterScreenNode = AboutCharacterScreenTestObj(characterExample)

            onNode(comicScreenNode.charactersList).performClick()

            onNode(characterScreenNode.seeAllTemplate).performClick()

            onNode(AllScreenTestObj.AllTemplate).assertExists()
            onNode(characterScreenNode.characterTemplate).assertDoesNotExist()
            onNode(AllScreenTestObj.allCharacterSeriesCard).assertExists()
        }
    }
}