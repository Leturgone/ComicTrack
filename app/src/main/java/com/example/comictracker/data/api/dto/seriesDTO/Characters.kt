package com.example.comictracker.data.api.dto.seriesDTO

import com.google.gson.annotations.SerializedName


/**
 * Characters
 *
 * @property available
 * @property returned
 * @property collectionURI
 * @property items
 * @constructor Create empty Characters
 */
data class Characters (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()

)