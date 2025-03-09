package com.example.comictracker.data.api.dto.comicsDTO

import com.example.comictracker.data.api.dto.Thumbnail
import com.example.comictracker.data.api.dto.Urls
import com.google.gson.annotations.SerializedName


data class Results (

    @SerializedName("id"                 ) var id                 : String,
    @SerializedName("digitalId"          ) var digitalId          : String,
    @SerializedName("title"              ) var title              : String,
    @SerializedName("issueNumber"        ) var issueNumber        : String,
    @SerializedName("variantDescription" ) var variantDescription : String,
    @SerializedName("description"        ) var description        : String,
    @SerializedName("modified"           ) var modified           : String,
    @SerializedName("isbn"               ) var isbn               : String,
    @SerializedName("upc"                ) var upc                : String,
    @SerializedName("diamondCode"        ) var diamondCode        : String,
    @SerializedName("ean"                ) var ean                : String,
    @SerializedName("issn"               ) var issn               : String,
    @SerializedName("format"             ) var format             : String,
    @SerializedName("pageCount"          ) var pageCount          : String,
    @SerializedName("textObjects"        ) var textObjects        : ArrayList<TextObjects>     = arrayListOf(),
    @SerializedName("resourceURI"        ) var resourceURI        : String?                    = "",
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