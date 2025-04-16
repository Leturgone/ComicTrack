package com.example.comictracker.data.api.dto.comicsDTO

import com.google.gson.annotations.SerializedName


/**
 * Collections
 *
 * @property resourceURI
 * @property name
 * @constructor Create empty Collections
 */
data class Collections (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)