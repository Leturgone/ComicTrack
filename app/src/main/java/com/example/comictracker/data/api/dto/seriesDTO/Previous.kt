package com.example.comictracker.data.api.dto.seriesDTO

import com.google.gson.annotations.SerializedName


/**
 * Previous
 *
 * @property resourceURI
 * @property name
 * @constructor Create empty Previous
 */
data class Previous (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)