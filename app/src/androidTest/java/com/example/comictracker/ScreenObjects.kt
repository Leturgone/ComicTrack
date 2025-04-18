package com.example.comictracker

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText


object HomeScreen{
    val newReleasesTemplate = (hasText("New releases")) and hasNoClickAction()
    val newReleasesCard = hasContentDescription("  current cover")
    val continueReadingTemplate = (hasText("Continue reading")) and hasNoClickAction()
    val continueReadingList = hasContentDescription("  current cover")
    val seeAllNewTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllNew")
    val seeAllContinueTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllNew")
}

object SearchScreen{
    val SearchTemplate = (hasText("Search comics")) and hasNoClickAction()
    val SearchBar = (hasText("Search")) and hasTestTag("searchBar")
    val mayLikeTemplate = (hasText("May like")) and hasNoClickAction()
    val mayLikeList = hasContentDescription("  current cover")
    val seeAllNewTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeMayLike")
    val discoverSeriesTemplate = (hasText("Discover series")) and hasNoClickAction()
    val seeAllDiscoverTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllDiscover")
    val discoverList = hasContentDescription("  current cover")
    val charactersTemplate = (hasText("Characters")) and hasNoClickAction()
    val seeAllCharactersTemplate = (hasText("See all")) and hasClickAction() and hasTestTag("seeAllCharacters")
    val characterList = hasContentDescription("  current cover")
}

object LibraryScreen{
    val LibraryTemplate = (hasText("My Library")) and hasNoClickAction()
    val ComicsTemplate = (hasText("Comics")) and hasClickAction()
    val SeriesTemplate = (hasText("Series")) and hasClickAction()
    val ReadlistTemplate = (hasText("Readlist")) and hasClickAction()
    val FavoriteTemplate = (hasText("Favorites")) and hasNoClickAction()


}

object AboutCharacterScreen{

}
object AboutComicScreen{

}

object AboutSeriesScreen{

}