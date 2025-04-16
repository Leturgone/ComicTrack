package com.example.comictracker.data.api.dto

import com.google.gson.annotations.SerializedName


/**
 * Thumbnail
 *
 * @property path
 * @property extension
 * @constructor Create empty Thumbnail
 */
data class Thumbnail (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)