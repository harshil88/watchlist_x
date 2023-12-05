package com.harshilpadsala.watchlistx.data

import com.google.gson.annotations.SerializedName

data class ImageDetails(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double?,
    val height: Int?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("file_path")
    val filePath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    val width: Int?
)
