package com.example.comictracker.data.api.dto

import com.google.gson.annotations.SerializedName


/**
 * Urls
 *
 * @property type
 * @property url
 * @constructor Create empty Urls
 */
data class Urls (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)