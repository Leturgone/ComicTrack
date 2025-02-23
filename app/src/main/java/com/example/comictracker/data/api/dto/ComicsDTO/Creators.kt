package com.example.comictracker.data.api.dto.ComicsDTO

import com.example.comictracker.data.api.dto.SeriesDTO.Items
import com.google.gson.annotations.SerializedName


data class Creators (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()

)