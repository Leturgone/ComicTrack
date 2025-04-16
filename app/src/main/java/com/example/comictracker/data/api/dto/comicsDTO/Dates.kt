package com.example.comictracker.data.api.dto.comicsDTO

import com.google.gson.annotations.SerializedName


/**
 * Dates
 *
 * @property type
 * @property date
 * @constructor Create empty Dates
 */
data class Dates (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("date" ) var date : String? = null

)