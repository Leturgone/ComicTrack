package com.example.comictracker.data.api.dto

import com.google.gson.annotations.SerializedName

/**
 * Items x
 *
 * @property resourceURI
 * @property name
 * @property role
 * @constructor Create empty Items x
 */
data class ItemsX (

    @SerializedName("resourceURI" ) var resourceURI : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("role"        ) var role        : String? = null,
)