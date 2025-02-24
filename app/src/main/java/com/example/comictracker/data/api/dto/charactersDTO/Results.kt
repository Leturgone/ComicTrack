package com.example.comictracker.data.api.dto.charactersDTO


import com.example.comictracker.data.api.dto.Thumbnail
import com.example.comictracker.data.api.dto.Urls
import com.example.comictracker.domain.model.CharacterModel
import com.example.comictracker.domain.model.ComicModel
import com.google.gson.annotations.SerializedName


data class Results (

    @SerializedName("id"          ) var id          : String,
    @SerializedName("name"        ) var name        : String,
    @SerializedName("description" ) var description : String,
    @SerializedName("modified"    ) var modified    : String,
    @SerializedName("resourceURI" ) var resourceURI : String,
    @SerializedName("urls"        ) var urls        : ArrayList<Urls>,
    @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail,
    @SerializedName("comics"      ) var comics      : Comics,
    @SerializedName("stories"     ) var stories     : Stories,
    @SerializedName("events"      ) var events      : Events,
    @SerializedName("series"      ) var series      : Series         = Series()

)