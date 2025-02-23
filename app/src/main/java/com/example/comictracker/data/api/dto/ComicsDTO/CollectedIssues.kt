package com.example.comictracker.data.api.dto.ComicsDTO

import com.google.gson.annotations.SerializedName


data class CollectedIssues (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)