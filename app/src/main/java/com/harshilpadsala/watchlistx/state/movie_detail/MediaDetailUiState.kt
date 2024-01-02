package com.harshilpadsala.watchlistx.state.movie_detail

import com.harshilpadsala.watchlistx.data.res.detail.MovieDetailPresenter

sealed interface MovieDetailUiState {


    object Loading : MovieDetailUiState

    data class MovieDetailsSuccess(val data: MovieDetailPresenter?) : MovieDetailUiState

    data class Error(val message: String?) : MovieDetailUiState


}
