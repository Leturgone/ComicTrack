package com.example.comictracker.data.api.dto.seriesDTO

import com.google.gson.annotations.SerializedName


data class Results (

    @SerializedName("id"          ) var id          : String?         = null,
    @SerializedName("title"       ) var title       : String?         = null,
    @SerializedName("description" ) var description : String?         = null,
    @SerializedName("resourceURI" ) var resourceURI : String?         = null,
    @SerializedName("urls"        ) var urls        : ArrayList<Urls> = arrayListOf(),
    @SerializedName("startYear"   ) var startYear   : String?         = null,
    @SerializedName("endYear"     ) var endYear     : String?         = null,
    @SerializedName("rating"      ) var rating      : String?         = null,
    @SerializedName("modified"    ) var modified    : String?         = null,
    @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail?      = Thumbnail(),
    @SerializedName("comics"      ) var comics      : Comics?         = Comics(),
    @SerializedName("stories"     ) var stories     : Stories?        = Stories(),
    @SerializedName("events"      ) var events      : Events?         = Events(),
    @SerializedName("characters"  ) var characters  : Characters?     = Characters(),
    @SerializedName("creators"    ) var creators    : Creators?       = Creators(),
    @SerializedName("next"        ) var next        : Next?           = Next(),
    @SerializedName("previous"    ) var previous    : Previous?       = Previous()

)