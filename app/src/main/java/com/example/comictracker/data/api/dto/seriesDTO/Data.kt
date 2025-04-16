package com.example.comictracker.data.api.dto.seriesDTO

import com.google.gson.annotations.SerializedName


/**
 * Data
 *
 * @property offset
 * @property limit
 * @property total
 * @property count
 * @property results
 * @constructor Create empty Data
 */
data class Data (

  @SerializedName("offset"  ) var offset  : String?            = null,
  @SerializedName("limit"   ) var limit   : String?            = null,
  @SerializedName("total"   ) var total   : String?            = null,
  @SerializedName("count"   ) var count   : String?            = null,
  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)