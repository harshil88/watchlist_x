package com.harshilpadsala.watchlistx.data.res.list

import com.google.gson.annotations.SerializedName

data class Content<T>(
    val page: Int?,
    val results: List<T>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)


