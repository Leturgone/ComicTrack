package com.example.comictracker.data.api.dto.charactersDTO

import com.google.gson.annotations.SerializedName


/**
 * Series
 *
 * @property available
 * @property returned
 * @property collectionURI
 * @property items
 * @constructor Create empty Series
 */
data class Series (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()

)