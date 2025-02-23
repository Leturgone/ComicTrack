package com.example.comictracker.data.api.dto.creatorsDTO

import com.example.comictracker.data.api.dto.seriesDTO.Items
import com.google.gson.annotations.SerializedName


data class Series (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()

)