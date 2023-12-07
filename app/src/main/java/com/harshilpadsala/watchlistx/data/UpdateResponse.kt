package com.harshilpadsala.watchlistx.data

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    val success: Boolean?,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?
)
