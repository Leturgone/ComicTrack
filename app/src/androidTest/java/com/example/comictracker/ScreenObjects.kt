package com.example.comictracker

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasInsertTextAtCursorAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.SeriesModel


object HomeScreenTestObj{
    val newReleasesTemplate = (hasText("New releases")) and hasNoClickAction()
    val newReleasesCard = hasContentDescription("${comicExample.title}  current cover")
    val continueReadingTemplate = (hasText("Continue reading")) and hasNoClickAction()
    val continueReadingList = hasContentDescription("${secondComicExample.title}  current cover")
    val seeAllNewTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllNew")
    val seeAllContinueTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllContinueReading")
    val newComicError = hasText("Error while loading new comics")
    val nextComicError = hasText("Error while loading next comics")
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
    val seeAllCharactersTemplate = hasTestTag("seeAllCharacters")
    val characterList = hasContentDescription("${characterExample.name} character")
    val discoverSeriesError = hasText("Error while loading Discover Series")
    val mayLikeSeriesError = hasText("Error while loading May Like Series")
    val characterListError = hasText("Error while loading characters")
}

object LibraryScreenTestObj{
    val libraryTemplate = (hasText("My Library")) and hasNoClickAction()
    val comicsTemplate = (hasText("Comics")) and hasClickAction()
    val seriesTemplate = (hasText("Series")) and hasClickAction()
    val readlistTemplate = (hasText("Readlist")) and hasClickAction()
    val favoriteTemplate = (hasText("Favorites")) and hasNoClickAction()
    val favoritesList = hasContentDescription("${secondSeriesExample.title}  current cover")
    val curReadTemplate = (hasText("Currently reading")) and hasNoClickAction()
    val curReadList = hasContentDescription("${seriesExample.title}  current cover")
    val lastUpdatesTemplate = (hasText("Last updates")) and hasNoClickAction()
    val lastUpdatesList = hasContentDescription("${comicExample.title}  current cover")
    val seeAllCurrentTemplate =  (hasText("See all")) and hasClickAction() and hasTestTag("seeAllCurrent")
    val seeAllLastTemplate =  (hasText("See all")) and hasClickAction() and hasTestTag("seeAllLast")
    val favoriteError = hasText("Error while loading favorite series")
    val currentError = hasText("Error while loading current read series")
    val historyError = hasText("Error while loading comics")
    val statisticsError = hasText("Error while loading statistics")
}

class AboutCharacterScreenTestObj(character:CharacterModel){
    val characterTemplate = (hasText(character.name))
    val descTemplate = (hasText("DESCRIPTION"))
    val characterDesc = hasText(character.desc)
    val allTemplate = hasText("All")
    val seeAllTemplate = hasText("See all") and hasClickAction()
    val characterSeriesCard = hasContentDescription("${seriesExample.title}  current cover")
    val characterDataError = hasText("Error while loading character")
    val seriesError = hasText("Error while loading series with this character")

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
    val creatorsList = hasContentDescription("${creatorExample.name} creator")
    val charactersTemplate = hasText("Characters")
    val charactersList = hasContentDescription("${characterExample.name} character")
    val comicError = hasText("Error while loading this comic")
}

class AboutSeriesScreenTestObj(series: SeriesModel) {
    val titleTemplate = hasText(series.title ?: "No title")
    val dateTemplate = hasText("DATE")
    val seriesDateTemplate = hasText(series.date?:"No date")
    val descTemplate  = hasText("DESCRIPTION")
    val seeMoreTemplate = hasText("See More")
    val seeLessTemplate = hasText("See Less")
    val seriesDescTemplate = hasText(series.desc?:"No description")

    val favoriteMark = hasContentDescription("FavoriteMark")
    val unFavoriteMark = hasContentDescription("UnfavoriteMark")

    val bottomSheetTemplate = hasText("Choose Category")
    val bottomSheetReadMark = hasContentDescription("Read icon")
    val bottomSheetUnreadMark = hasContentDescription("Unread icon")
    val bottomSheetCurrentlyMark = hasContentDescription("Currently reading icon")
    val bottomSheetWillBeReadMark = hasContentDescription("Will be read icon")

    val readTemplate = hasText("Read")
    val willBeReadTemplate = hasText("Will be read")
    val currentlyReadingTemplate = hasText("Currently reading")
    val unreadTemplate = hasText("Unread")
    val continueReadingTemplate = hasText("Continue reading")
    val seeAllTemplate = hasText("See all") and hasClickAction()

    val nextReadItem = hasContentDescription("${comicExample.title}  current cover")

    val readItemMark = hasContentDescription("ReadIcon")

    val unreadItemMark = hasContentDescription("UnreadIcon")

    val secondNestReadItem = hasContentDescription("${secondComicExample.title}  current cover")

    val creatorsTemplate = hasText("Creators")
    val creatorsList = hasContentDescription("${creatorExample.name} creator")
    val charactersTemplate = hasText("Characters")
    val charactersList = hasContentDescription("${characterExample.name} character")
    val connectedTemplate = hasText("Connected")
    val connectedList = hasContentDescription("${secondSeriesExample.title}  current cover")

    val seriesScreenScroll = hasTestTag("seriesScreenScroll")
    val seriesError = hasText("Error while loading this series")
}

object AllScreenTestObj{
    val AllTemplate = hasText("All")
    val newReleasesCard = hasContentDescription("${comicExample.title}  current cover")
    val continueReadingCard = hasContentDescription("${secondComicExample.title}  current cover")
    val mayLikeSeriesCard = hasContentDescription("${secondSeriesExample.title}  current cover")
    val discoverSeriesCard = hasContentDescription("${seriesExample.title}  current cover")
    val allCharacters =  hasContentDescription("${characterExample.name} character")
    val allSeriesCard = hasContentDescription("${secondSeriesExample.title}  current cover")
    val allComicCard = hasContentDescription("${secondComicExample.title}  current cover")
    val allReadlistCard = hasContentDescription("${seriesExample.title}  current cover")
    val allCurrentlyCard = hasContentDescription("${seriesExample.title}  current cover")
    val allLastCard = hasContentDescription("${comicExample.title}  current cover")
    val allCharacterSeriesCard = hasContentDescription("${seriesExample.title}  current cover")
    val nextButton = hasContentDescription("nextButton")
    val backButton = hasContentDescription("backButton")
    val newReleasesNextButtonCard = hasContentDescription("${secondComicExample.title}  current cover")
    val discoverSeriesNextButtonCard = hasContentDescription("${secondSeriesExample.title}  current cover")
    val charactersNextButtonCard = hasContentDescription("ch12 character")
    val newComicsErrorMessage = hasText("Error while loading all new comics")
    val allCharacterErrorMessage = hasText("Error while loading comics with this character")
    val allCurrentReadingSeriesErrorMessage = hasText("Error while loading all current reading")
    val allDiscoverSeriesErrorMessage = hasText("Error while loading series")
    val allCharactersErrorMessage = hasText("Error while loading characters")
    val allLastComicsErrorMessage = hasText("Error while loading last comics")
    val allLibComicErrorMessage = hasText("Error while loading all comics from lib")
    val allLibSeriesErrorMessage = hasText("Error while loading all series from lib")
    val allMayLikeSeriesErrorMessage = hasText("Error while loading all may like series")
    val allNewComicErrorMessage = hasText("Error while loading all new comics")
    val allNextComicErrorMessage = hasText("Error while loading all next comics")
    val allReadListErrorMessage = hasText("Error while loading all readlist")

}

object AllComicFromSeriesScreenTestObj{
    val AllTemplate = hasText("All Comics")
    val comicListItem = hasContentDescription("${secondComicExample.title}  current cover")

    val comicList = hasTestTag("all_comics_from_series")
    val readItemMark = hasContentDescription("ReadIcon")

    val secondPageItem = hasContentDescription("${comicExample.title}  current cover")
    val unreadItemMark = hasContentDescription("UnreadIcon")

    val loadMoreButton = hasTestTag("load_more")
    val loadComicErrorMessage = hasText("Error while loading comic from this series")

}

object SearchResultScreenTestObj{
    val searchResultTemplate = hasText("Search result")
    val resCharacterCard = hasContentDescription("${characterExample.name} character")
    val resSeriesCard = hasContentDescription("${seriesExample.title}  current cover")
    val characterNotFoundMessage = hasText("Characters not found")
    val updateButton = hasText("Update") and hasClickAction()
    val notFoundErrorText = hasText("Not found")
}