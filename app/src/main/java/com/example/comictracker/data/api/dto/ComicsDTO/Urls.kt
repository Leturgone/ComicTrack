package com.example.comictracker.data.api.dto.ComicsDTO

import com.google.gson.annotations.SerializedName


data class Urls (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)