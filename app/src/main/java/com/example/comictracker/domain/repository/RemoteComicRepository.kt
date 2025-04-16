package com.example.comictracker.domain.repository

import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.model.SeriesModel

/**
 * Remote comic repository
 *
 * @constructor Create empty Remote comic repository
 */
interface RemoteComicRepository {

    /**
     * Get series last releases by id
     *
     * @param id
     * @return
     */
    suspend fun getSeriesLastReleasesById(id: Int): List<ComicModel>

    /**
     * Get character series
     *
     * @param characterId
     * @param offset
     * @return
     */
    suspend fun getCharacterSeries(characterId: Int, offset:Int = 0): List<SeriesModel>

    /**
     * Get all series
     *
     * @param loadedCount
     * @return
     */
    suspend fun getAllSeries(loadedCount: Int = 0):List<SeriesModel>

    /**
     * Get all characters
     *
     * @param loadedCount
     * @return
     */
    suspend fun getAllCharacters(loadedCount: Int = 0) :List<CharacterModel>

    /**
     * Get series by title
     *
     * @param title
     * @return
     */
    suspend fun getSeriesByTitle(title:String):List<SeriesModel>

    /**
     * Get characters by name
     *
     * @param name
     * @return
     */
    suspend fun getCharactersByName(name: String): List<CharacterModel>

    /**
     * Get series by id
     *
     * @param id
     * @return
     */
    suspend fun getSeriesById(id:Int):SeriesModel

    /**
     * Get series creators
     *
     * @param creatorsRoles
     * @return
     */
    suspend fun getSeriesCreators(creatorsRoles: List<Pair<Int, String>>):List<CreatorModel>

    /**
     * Get series characters
     *
     * @param seriesId
     * @return
     */
    suspend fun getSeriesCharacters(seriesId: Int):List<CharacterModel>

    /**
     * Get connected series
     *
     * @param connectedSeriesId
     * @return
     */
    suspend fun getConnectedSeries(connectedSeriesId: List<Int?>): List<SeriesModel>

    /**
     * Get comics from series
     *
     * @param seriesId
     * @param loadedCount
     * @return
     */
    suspend fun getComicsFromSeries(seriesId: Int, loadedCount: Int = 0): List<ComicModel>

    /**
     * Get comic by id
     *
     * @param comicId
     * @return
     */
    suspend fun getComicById(comicId:Int): ComicModel

    /**
     * Get character by id
     *
     * @param characterId
     * @return
     */
    suspend fun getCharacterById(characterId: Int): CharacterModel

    /**
     * Get comic creators
     *
     * @param creatorsRoles
     * @return
     */
    suspend fun  getComicCreators(creatorsRoles: List<Pair<Int, String>>):List<CreatorModel>

    /**
     * Get comic characters
     *
     * @param comicId
     * @return
     */
    suspend fun getComicCharacters(comicId: Int):List<CharacterModel>

    /**
     * Load may like series ids
     *
     * @param loadedIdsSeriesFromBD
     * @return
     */
    suspend fun loadMayLikeSeriesIds(loadedIdsSeriesFromBD:List<Int>):List<Int>

    /**
     * Get previous comic id
     *
     * @param seriesId
     * @param number
     * @return
     */
    suspend fun getPreviousComicId(seriesId: Int, number: Int): Int?

    /**
     * Get next comic id
     *
     * @param seriesId
     * @param number
     * @return
     */
    suspend fun getNextComicId(seriesId: Int, number: Int): Int?
}