package com.example.comictracker.data.api

import com.example.comictracker.data.api.dto.charactersDTO.CharactersDTO
import com.example.comictracker.data.api.dto.comicsDTO.ComicsDTO
import com.example.comictracker.data.api.dto.creatorsDTO.CreatorsDTO
import com.example.comictracker.data.api.dto.seriesDTO.SeriesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelComicApi {

    @GET("/v1/public/comics")
    suspend fun getSeriesLastReleasesById(
        @Query("format")format:String = "comic",
        @Query("formatType")formatType:String = "comic",
        @Query("noVariants")noVariants:String = "true",
        @Query("dateDescriptor")dateDescriptor:String = "thisWeek",
        @Query("series")series:String,
        @Query("limit")limit:String = "80",

        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):ComicsDTO

    @GET("/v1/public/characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Query("characterId")characterId: Int,
        @Query("contains")contains:String = "comic",
        @Query("orderBy")orderBy:String = "startYear",
        @Query("limit")limit:String = "80",

        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):SeriesDTO

    @GET("/v1/public/series")
    suspend fun getAllSeries(
        @Query("limit")limit:String = "80",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()

    ):SeriesDTO

    @GET("/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("limit")limit:String = "80",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash(),
    ):CharactersDTO

    @GET("/v1/public/series")
    suspend fun getSeriesByTitle(
        @Query("titleStartsWith")titleStartsWith:String,
        @Query("contains")contains:String = "comic",
        @Query("orderBy")orderBy:String = "startYear",
        @Query("limit")limit:String = "80",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):SeriesDTO


    @GET("/v1/public/series/{seriesId}")
    suspend fun getSeriesById(
        @Query("seriesId")seriesId:String,
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()

    ):SeriesDTO

    @GET("/v1/public/series/{seriesId}/creators")
    suspend fun getSeriesCreators(
        @Query("seriesId")seriesId:String,
        @Query("limit")limit:String = "20",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):CreatorsDTO

    @GET("/v1/public/series/{seriesId}/characters")
    suspend fun getSeriesCharacters(
        @Query("seriesId")seriesId:String,
        @Query("limit")limit:String = "20",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):CharactersDTO

    @GET("/v1/public/series/{seriesId}/comics")
    suspend fun getComicsFromSeries(
        @Query("seriesId")seriesId:String,
        @Query("format")format:String = "comic",
        @Query("formatType")formatType:String = "comic",
        @Query("noVariants")noVariants:String = "true",
        @Query("orderBy")orderBy:String = "issueNumber",
        @Query("limit")limit:String = "90",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):ComicsDTO


    @GET("/v1/public/comics/{comicId}")
    suspend fun getComicById(
        @Query("comicId")comicId:String,
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):ComicsDTO
    @GET("/v1/public/comics/{comicId}/creators")
    suspend fun getComicCreators(
        @Query("comicId")comicId:String,
        @Query("limit")limit:String = "20",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):CreatorsDTO
    @GET("/v1/public/comics/{comicId}/characters")
    suspend fun getComicCharacters(
        @Query("comicId")comicId:String,
        @Query("limit")limit:String = "20",
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):CharactersDTO


    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Query("characterId")characterId:String,
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):CharactersDTO

    @GET("/v1/public/creators/{creatorId}")
    suspend fun getCreatorById(
        @Query("characterId")characterId:String,
        @Query("apikey")apikey:String=API_KEY,
        @Query("ts")ts:String=timeStamp,
        @Query("hash")hash:String=hash()
    ):CreatorsDTO


}