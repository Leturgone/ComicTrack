package com.example.comictracker.data.api.dto.comicsDTO

import com.example.comictracker.data.api.dto.ItemsX
import com.google.gson.annotations.SerializedName


/**
 * Creators
 *
 * @property available
 * @property returned
 * @property collectionURI
 * @property items
 * @constructor Create empty Creators
 */
data class Creators (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<ItemsX> = arrayListOf()

)