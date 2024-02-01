package com.harshilpadsala.watchlistx.data.res.model

import com.harshilpadsala.watchlistx.vm.RatingUiState

data class RatingArgsModel(
    val movieId : Int? = null,
    val movieName : String? = null,
    val isRated : Boolean? = null,
    val ratings : Int? = null,
    val posterPath : String? = null,
)

fun RatingArgsModel.toRatingUiState() : RatingUiState = RatingUiState(
   isRated= isRated,
   currentRating = ratings,
    movieName = movieName,
    movieId = movieId,
    posterPath = posterPath,
)
