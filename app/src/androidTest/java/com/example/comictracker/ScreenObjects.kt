package com.example.comictracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasInsertTextAtCursorAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel
import com.example.comictracker.presentation.ui.components.BottomNavigation


object HomeScreenTestObj{
    val newReleasesTemplate = (hasText("New releases")) and hasNoClickAction()
    val newReleasesCard = hasContentDescription("${comicExample.title}  current cover")
    val continueReadingTemplate = (hasText("Continue reading")) and hasNoClickAction()
    val continueReadingList = hasContentDescription("${secondComicExample.title}  current cover")
    val seeAllNewTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllNew")
    val seeAllContinueTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllContinueReading")
}

object BottomBarTestObj{
    val homeTemplate = hasContentDescription("home")
    val searchTemplate = hasContentDescription("search")
    val libraryTemplate = hasContentDescription("library")
}

object SearchScreenTestObj{
    val searchTemplate = (hasText("Search comics")) and hasNoClickAction()
    val searchBar =  hasTestTag("searchBar")
    val searchEditText = hasText("Search") and hasInsertTextAtCursorAction()
    val mayLikeTemplate = (hasText("May like")) and hasNoClickAction()
    val mayLikeList = hasContentDescription("${secondSeriesExample.title}  current cover")
    val seeAllMayLikeTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllMayLike")
    val discoverSeriesTemplate = (hasText("Discover series")) and hasNoClickAction()
    val seeAllDiscoverTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllDiscover")
    val discoverList = hasContentDescription("${seriesExample.title}  current cover")
    val charactersTemplate = (hasText("Characters")) and hasNoClickAction()
    val seeAllCharactersTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllCharacters")
    val characterList = hasContentDescription("${characterExample.name} character")
}

object LibraryScreenTestObj{
    val libraryTemplate = (hasText("My Library")) and hasNoClickAction()
    val comicsTemplate = (hasText("Comics")) and hasClickAction()
    val seriesTemplate = (hasText("Series")) and hasClickAction()
    val readlistTemplate = (hasText("Readlist")) and hasClickAction()
    val favoriteTemplate = (hasText("Favorites")) and hasNoClickAction()
    val favoritesList = hasContentDescription("  current cover")
    val curReadTemplate = (hasText("Currently reading")) and hasNoClickAction()
    val curReadList = hasContentDescription("  current cover")
    val lastUpdatesTemplate = (hasText("Last updates")) and hasNoClickAction()
    val lastUpdatesList = hasContentDescription("  current cover")


}

class AboutCharacterScreenTestObj(character:CharacterModel){
    val characterTemplate = (hasText(character.name))
    val descTemplate = (hasText("DESCRIPTION"))
    val characterDesc = hasText(character.desc)
    val allTemplate = hasText("All")
    val seeAllTemplate = hasText("See all") and hasClickAction()
    val lastUpdatesList = hasContentDescription("${character.series.first()}  current cover")

}

class AboutComicScreenTestObj(comic: ComicModel) {
    val titleTemplate = hasText(comic.title)
    val seriesTemplate = hasText("SERIES")
    val seriesTitleTemplate = hasText(comic.seriesTitle)
    val dateTemplate = hasText("DATE")
    val comicDateTemplate = hasText(comic.date)
    val markReadTemplates = hasText("Mark read")
    val markUnreadTemplate = hasText("Mark unread")
    val creatorsTemplate = hasText("Creators")
    val creatorsList = hasText("creator")
    val charactersTemplate = hasText("Characters")
    val charactersList = hasText("character")
}

class AboutSeriesScreenTestObj(series: SeriesModel) {
    val titleTemplate = hasText(series.title ?: "No title")
    val dateTemplate = hasText("DATE")
    val comicDateTemplate = hasText(series.date?:"No date")
    val descTemplate  = hasText("DESCRIPTION")
    val seriesDescTemplate = hasText(series.desc?:"No description")
    val markTemplate = hasText(series.readMark)
    val readTemplate = hasText("Read")
    val willBeReadTemplate = hasText("Will be read")
    val currentlyReadingTemplate = hasText("Currently reading")
    val unreadTemplate = hasText("Unread")
    val continueReadingTemplate = hasText("Continue reading")
    val seeAllTemplate = hasText("See all") and hasClickAction()
    val nextReadItem = hasContentDescription("current cover")
    val creatorsTemplate = hasText("Creators")
    val creatorsList = hasText("creator")
    val charactersTemplate = hasText("Characters")
    val charactersList = hasText("character")
    val connectedTemplate = hasText("Connected")
    val connectedList = hasText(series.connectedSeries.first().toString())
    val noConnectedList = hasText("No connected series")
}

object AllScreenTestObj{
    val AllTemplate = hasText("All")
    val newReleasesCard = hasContentDescription("${comicExample.title}  current cover")
    val continueReadingCard = hasContentDescription("${secondComicExample.title}  current cover")
    val mayLikeSeriesCard = hasContentDescription("${secondSeriesExample.title}  current cover")
    val discoverSeriesCard = hasContentDescription("${seriesExample.title}  current cover")
    val allCharacters =  hasContentDescription("${characterExample.name} character")
}
object AllComicFromSeriesScreenTestObj{
    val AllTemplate = hasText("All Comics")

}

object SearchResultScreenTestObj{
    val searchResultTemplate = hasText("Search result")
    val resCharacterCard = hasContentDescription("${characterExample.name} character")
    val resSeriesCard = hasContentDescription("${seriesExample.title}  current cover")
    val characterNotFoundMessage = hasText("Characters not found")
    val updateButton = hasText("Update") and hasClickAction()
    val notFoundErrorText = hasText("Not found")
}