package com.example.comictracker.data.api.dto.seriesDTO

import com.google.gson.annotations.SerializedName


/**
 * Comics
 *
 * @property available
 * @property returned
 * @property collectionURI
 * @property items
 * @constructor Create empty Comics
 */
data class Comics (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()

)