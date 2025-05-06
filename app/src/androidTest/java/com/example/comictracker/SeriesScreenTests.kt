package com.example.comictracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
import org.junit.Rule
import org.junit.Test
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

    @Test
    fun existTest(){}

    @Test
    fun markAsReadTest(){}

    @Test
    fun markAsUnreadTest(){}

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