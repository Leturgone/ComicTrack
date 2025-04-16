package com.example.comictracker.data.api.dto.comicsDTO

import com.google.gson.annotations.SerializedName


/**
 * Text objects
 *
 * @property type
 * @property language
 * @property text
 * @constructor Create empty Text objects
 */
data class TextObjects (

  @SerializedName("type"     ) var type     : String? = null,
  @SerializedName("language" ) var language : String? = null,
  @SerializedName("text"     ) var text     : String? = null

)