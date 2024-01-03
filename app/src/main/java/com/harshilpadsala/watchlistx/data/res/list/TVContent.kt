package com.harshilpadsala.watchlistx.data.res.list

import com.google.gson.annotations.SerializedName
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData

data class TVContent(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int?,
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)

fun TVContent.toListItemX(): ListItemXData = ListItemXData(
    id = this.id ?: 0,
    title = this.name ?: "",
    voteAverage = this.voteAverage ?: 0.0,
    posterPath = this.posterPath ?: "",
    releaseDate = this.firstAirDate ?: "",
    originalLanguage = this.originalLanguage ?: "",
)
