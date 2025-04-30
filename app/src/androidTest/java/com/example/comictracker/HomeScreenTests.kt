package com.example.comictracker

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.comictracker.domain.repository.local.LocalReadRepository
import com.example.comictracker.domain.repository.remote.RemoteComicsRepository
import com.example.comictracker.presentation.ui.screens.homeScreen.HomeScreen
import com.example.comictracker.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class HomeScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    val newReleasesMockList = listOf(
        comicExample
    )

    val continueReadingMockList = listOf(
        secondComicExample
    )

    @Mock
    lateinit var remoteComicRepository:RemoteComicsRepository

    @Mock
    lateinit var localReadRepository:LocalReadRepository

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }
    
    @Test
    fun existTest() = runTest{

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

        val viewModel = HomeScreenViewModel(remoteComicRepository,localReadRepository)


        composeTestRule.run {
            setContent{ HomeScreen(rememberNavController(), viewModel) }
            onNode(HomeScreen.newReleasesTemplate).assertExists()
            onNode(HomeScreen.newReleasesCard).assertExists()
            onNode(HomeScreen.continueReadingTemplate).assertExists()
            onNode(HomeScreen.continueReadingList).assertExists()

            onNode(HomeScreen.seeAllNewTemplate).assertExists()
            onNode(HomeScreen.seeAllContinueTemplate).assertExists()

        }
    }
}