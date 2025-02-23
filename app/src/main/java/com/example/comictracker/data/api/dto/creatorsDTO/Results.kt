package com.example.comictracker.data.api.dto.creatorsDTO

import com.example.comictracker.data.api.dto.charactersDTO.Series
import com.example.comictracker.data.api.dto.seriesDTO.Comics
import com.example.comictracker.data.api.dto.seriesDTO.Events
import com.example.comictracker.data.api.dto.seriesDTO.Stories
import com.example.comictracker.data.api.dto.seriesDTO.Thumbnail
import com.example.comictracker.data.api.dto.seriesDTO.Urls
import com.google.gson.annotations.SerializedName


data class Results (

    @SerializedName("id"          ) var id          : String?         = null,
    @SerializedName("firstName"   ) var firstName   : String?         = null,
    @SerializedName("middleName"  ) var middleName  : String?         = null,
    @SerializedName("lastName"    ) var lastName    : String?         = null,
    @SerializedName("suffix"      ) var suffix      : String?         = null,
    @SerializedName("fullName"    ) var fullName    : String?         = null,
    @SerializedName("modified"    ) var modified    : String?         = null,
    @SerializedName("resourceURI" ) var resourceURI : String?         = null,
    @SerializedName("urls"        ) var urls        : ArrayList<Urls> = arrayListOf(),
    @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail?      = Thumbnail(),
    @SerializedName("series"      ) var series      : Series?         = Series(),
    @SerializedName("stories"     ) var stories     : Stories?        = Stories(),
    @SerializedName("comics"      ) var comics      : Comics?         = Comics(),
    @SerializedName("events"      ) var events      : Events?         = Events()

)