package com.example.comictracker.data.api.dto.comicsDTO

import com.google.gson.annotations.SerializedName


/**
 * Series
 *
 * @property resourceURI
 * @property name
 * @constructor Create empty Series
 */
data class Series (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)