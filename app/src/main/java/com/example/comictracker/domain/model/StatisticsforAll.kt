package com.example.comictracker.domain.model

/**
 * Statisticsfor all
 *
 * @property comicCount
 * @property comicCountThisYear
 * @property seriesCount
 * @property seriesCountThisYear
 * @property readlistCount
 * @constructor Create empty Statisticsfor all
 */
data class StatisticsforAll(
    val comicCount: Int ,
    val comicCountThisYear: Int,
    val seriesCount: Int,
    val seriesCountThisYear :Int,
    val readlistCount:Int
)
