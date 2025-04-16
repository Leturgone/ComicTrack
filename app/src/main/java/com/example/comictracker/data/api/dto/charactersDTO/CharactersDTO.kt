package com.example.comictracker.data.api.dto.charactersDTO

import com.google.gson.annotations.SerializedName


/**
 * Characters d t o
 *
 * @property code
 * @property status
 * @property copyright
 * @property attributionText
 * @property attributionHTML
 * @property data
 * @property etag
 * @constructor Create empty Characters d t o
 */
data class CharactersDTO (

    @SerializedName("code"            ) var code            : String? = null,
    @SerializedName("status"          ) var status          : String? = null,
    @SerializedName("copyright"       ) var copyright       : String? = null,
    @SerializedName("attributionText" ) var attributionText : String? = null,
    @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
    @SerializedName("data"            ) var data            : Data?   = Data(),
    @SerializedName("etag"            ) var etag            : String? = null

)