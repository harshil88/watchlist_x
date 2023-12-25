package com.harshilpadsala.watchlistx.state.movie_detail

import com.harshilpadsala.watchlistx.data.res.detail.ActorDetails
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails

sealed interface MovieDetailUiState {

    object Loading : MovieDetailUiState

    data class Success(val data : MovieDetails?) : MovieDetailUiState

    data class Error(val message : String?) : MovieDetailUiState

}