package com.example.comictracker.data.api.dto.comicsDTO

import com.google.gson.annotations.SerializedName


/**
 * Collected issues
 *
 * @property resourceURI
 * @property name
 * @constructor Create empty Collected issues
 */
data class CollectedIssues (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)