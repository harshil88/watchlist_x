package com.harshilpadsala.watchlistx.data.res.model

import com.harshilpadsala.watchlistx.state.RatingUiState

data class RatingArgsModel(
    val movieId : Int?,
    val movieName : String?,
    val isRated : Boolean?,
    val ratings : Int?,
    val posterPath : String?,
)

fun RatingArgsModel.toRatingUiState() : RatingUiState = RatingUiState(
   isRated= isRated,
   currentRating = ratings,
    movieName = movieName,
    movieId = movieId,
    posterPath = posterPath,
)
