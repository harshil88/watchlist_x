package com.harshilpadsala.watchlistx.data.res.list

import com.google.gson.annotations.SerializedName
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData

data class Movie(
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)


fun Movie.toListItemX(): ListItemXData = ListItemXData(
    id = this.id ?: 0,
    title = this.title ?: "",
    voteAverage = this.voteAverage ?: 0.0,
    posterPath = this.posterPath ?: "",
    releaseDate = this.releaseDate ?: "",
    originalLanguage = this.originalLanguage ?: "",
)



