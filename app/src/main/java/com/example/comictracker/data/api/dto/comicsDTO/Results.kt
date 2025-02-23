package com.example.comictracker.data.api.dto.comicsDTO

import com.example.comictracker.data.api.dto.charactersDTO.Series
import com.example.comictracker.data.api.dto.seriesDTO.Characters
import com.example.comictracker.data.api.dto.seriesDTO.Creators
import com.example.comictracker.data.api.dto.seriesDTO.Events
import com.example.comictracker.data.api.dto.seriesDTO.Stories
import com.example.comictracker.data.api.dto.seriesDTO.Thumbnail
import com.example.comictracker.data.api.dto.seriesDTO.Urls
import com.google.gson.annotations.SerializedName


data class Results (

    @SerializedName("id"                 ) var id                 : String?                    = null,
    @SerializedName("digitalId"          ) var digitalId          : String?                    = null,
    @SerializedName("title"              ) var title              : String?                    = null,
    @SerializedName("issueNumber"        ) var issueNumber        : String?                    = null,
    @SerializedName("variantDescription" ) var variantDescription : String?                    = null,
    @SerializedName("description"        ) var description        : String?                    = null,
    @SerializedName("modified"           ) var modified           : String?                    = null,
    @SerializedName("isbn"               ) var isbn               : String?                    = null,
    @SerializedName("upc"                ) var upc                : String?                    = null,
    @SerializedName("diamondCode"        ) var diamondCode        : String?                    = null,
    @SerializedName("ean"                ) var ean                : String?                    = null,
    @SerializedName("issn"               ) var issn               : String?                    = null,
    @SerializedName("format"             ) var format             : String?                    = null,
    @SerializedName("pageCount"          ) var pageCount          : String?                    = null,
    @SerializedName("textObjects"        ) var textObjects        : ArrayList<TextObjects>     = arrayListOf(),
    @SerializedName("resourceURI"        ) var resourceURI        : String?                    = null,
    @SerializedName("urls"               ) var urls               : ArrayList<Urls>            = arrayListOf(),
    @SerializedName("series"             ) var series             : Series?                    = Series(),
    @SerializedName("variants"           ) var variants           : ArrayList<Variants>        = arrayListOf(),
    @SerializedName("collections"        ) var collections        : ArrayList<Collections>     = arrayListOf(),
    @SerializedName("collectedIssues"    ) var collectedIssues    : ArrayList<CollectedIssues> = arrayListOf(),
    @SerializedName("dates"              ) var dates              : ArrayList<Dates>           = arrayListOf(),
    @SerializedName("prices"             ) var prices             : ArrayList<Prices>          = arrayListOf(),
    @SerializedName("thumbnail"          ) var thumbnail          : Thumbnail?                 = Thumbnail(),
    @SerializedName("images"             ) var images             : ArrayList<Images>          = arrayListOf(),
    @SerializedName("creators"           ) var creators           : Creators?                  = Creators(),
    @SerializedName("characters"         ) var characters         : Characters?                = Characters(),
    @SerializedName("stories"            ) var stories            : Stories?                   = Stories(),
    @SerializedName("events"             ) var events             : Events?                    = Events()

)