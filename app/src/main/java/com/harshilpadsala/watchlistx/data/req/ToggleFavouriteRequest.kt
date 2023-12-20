package com.harshilpadsala.watchlistx.data.req

import com.google.gson.annotations.SerializedName

data class ToggleFavouriteRequest(
    @SerializedName("media_type") val mediaType: String?,
    @SerializedName("media_id") val mediaId: Int?,
    val favorite: Boolean? = null,
    val watchlist: Boolean? = null

)