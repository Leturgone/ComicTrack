package com.example.comictracker.data.api.dto.comicsDTO

import com.google.gson.annotations.SerializedName


/**
 * Variants
 *
 * @property resourceURI
 * @property name
 * @constructor Create empty Variants
 */
data class Variants (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)