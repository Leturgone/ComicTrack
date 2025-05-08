package com.example.comictracker

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel

val seriesExample = SeriesModel(
    seriesId=466,
    title="Ultimate Spider-Man (2000 - 2009)",
    date="2000 - 2009",
    desc="In 2000, Marvel embarked on a bold new experiment, re-imagining some of their greatest heroes in the 21st century, beginning with Spider-Man! Writer Brian Michael Bendis along with artists Mark Bagley and Stuart Immonen invite you to discover the world of Peter Parker in a whole new way with the series that changed everything!",
    image="http://i.annihil.us/u/prod/marvel/i/mg/6/c0/5149db8019dc9.jpg",
    comics= listOf(
        4372, 14846, 14857, 14868, 14879,
        14890, 14901, 14912, 14923, 14836,
        14837, 14838, 14839, 14840, 14841,
        14842, 14843, 14844, 14845, 14847
    ),
    creators= listOf(
        Pair(87, "penciler"), Pair(192, "penciler"), Pair(24, "writer"), Pair(210, "writer"),
        Pair(1909, "colorist"), Pair(556, "colorist"), Pair(13055, "colorist"), Pair(13568, "colorist"),
        Pair(4306, "colorist"), Pair(452, "other"), Pair(359, "inker"), Pair(552, "inker"),
        Pair(362, "inker"), Pair(548, "inker"), Pair(558, "inker"), Pair(13146, "inker"),
        Pair(454, "inker"), Pair(5052, "inker"), Pair(12581, "letterer"), Pair(774, "painter (cover)")
    ),
    characters= listOf(
        1015239, 1010908, 1011346, 1010910,
        1010913, 1010916, 1014991, 1009244,
        1010918, 1010919, 1009276, 1010921,
        1011358, 1010922, 1010923, 1011013,
        1011222, 1014974, 1010929, 1009619
    ),
    connectedSeries= listOf(8509, null),
    readMark="unread",
    favoriteMark=false)

val comicExample = ComicModel(
    comicId=4372,
    title="Ultimate Spider-Man (2000) #1",
    number="1",
    image="http://i.annihil.us/u/prod/marvel/i/mg/9/90/519b7e7eb534a.jpg",
    seriesId=466,
    seriesTitle="Ultimate Spider-Man (2000 - 2009)",
    date="06.09.2000",
    creators= listOf(
        Pair(87, "penciler"), Pair(24, "writer"), Pair(210, "writer"),
        Pair(1909, "colorist"),Pair(350, "letterer"), Pair(568, "inker")
    ),
    characters= listOf(1011010),
    readMark="unread"
)

val secondComicExample = ComicModel(
    comicId=9450,
    title="Iron Man (1968) #21",
    number="21",
    image="http://i.annihil.us/u/prod/marvel/i/mg/6/b0/4f637b98b6b12.jpg",
    seriesId=466,
    seriesTitle="Iron Man (1968 - 1996)",
    date="01.01.1970",
    creators= listOf(Pair(1178, "writer"), Pair(1847, "letterer"), Pair(1183, "penciler")),
    characters= listOf(1009368),
    readMark="unread"
)

val characterExample = CharacterModel(
    characterId=1009368,
    name="Iron Man",
    desc="Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man.",
    image="http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg",
    series= listOf(
        21112, 16450, 6079, 27392, 9790, 24380, 13896,
        4897, 20443, 2116, 454, 1489, 2984, 41609, 318,
        6056, 14818, 14779, 42695, 9792)
)

val creatorExample =  CreatorModel(
    creatorId = 1178,
    name = "Name",
    image = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg",
    role = "writer"
)

val secondSeriesExample = SeriesModel(
    seriesId=3897,
    title="Daredevil: The Man Without Fear (1993)",
    date="1993 - 1993",
    desc=null,
    image="http://i.annihil.us/u/prod/marvel/i/mg/3/c0/5d924c45980e1.jpg",
    comics= listOf(20750, 20751, 20752, 20753, 20754),
    creators= listOf(
        Pair(17, "writer"), Pair(13196, "penciler"),
        Pair(1759, "letterer"), Pair(6758, "colorist"),
        Pair(397, "inker")
    ),
    characters= listOf(1009262, 1009288, 1010786, 1009389),
    connectedSeries = listOf(null, null),
    readMark="unread",
    favoriteMark=false
)



