package com.harshilpadsala.watchlistx.state.movie_detail

sealed interface RatingUiState {

    object Loading : RatingUiState

    data class Success(val message : String) : RatingUiState

    data class Error(val message : String) : RatingUiState

}