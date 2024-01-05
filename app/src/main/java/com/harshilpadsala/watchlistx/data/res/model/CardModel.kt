package com.harshilpadsala.watchlistx.data.res.model

import com.harshilpadsala.watchlistx.data.res.detail.Rated

data class CardModel(
    val id: Int?,
    val title: String?,
    val imageUri : String?,
)

fun CardModel.toRatingArgsModel(value: Double?) : RatingArgsModel =
    RatingArgsModel(
        movieId = this.id,
        movieName = this.title,
        posterPath = this.imageUri,
        ratings = value?.toInt(),
        isRated = value != null,
    )

