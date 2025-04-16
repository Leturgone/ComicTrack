package com.example.comictracker.data.api.dto.creatorsDTO

import com.google.gson.annotations.SerializedName


/**
 * Items
 *
 * @property resourceURI
 * @property name
 * @constructor Create empty Items
 */
data class Items (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)