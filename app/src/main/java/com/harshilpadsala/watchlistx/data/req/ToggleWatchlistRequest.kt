package com.harshilpadsala.watchlistx.data.req

import com.google.gson.annotations.SerializedName

data class ToggleWatchlistRequest(
    @SerializedName("media_type") val mediaType: String?,
    @SerializedName("media_id") val mediaId: Int?,
    val watchlist: Boolean?
)
