package com.example.comictracker.data.api.dto.seriesDTO

import com.google.gson.annotations.SerializedName


/**
 * Items
 *
 * @property resourceURI
 * @property name
 * @property role
 * @constructor Create empty Items
 */
data class Items (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("role"        ) var role        : String? = null

)