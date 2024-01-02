package com.harshilpadsala.watchlistx.state

data class RatingUiState(
    var isRated : Boolean? = false,
    var currentRating : Int? = null,
    var isLoading : Boolean? = null,
    var movieName : String? = null,
    var movieId : Int? =  null,
    var posterPath : String? = null,
    var success : String? = null,
    var failure : String? = null,
)
