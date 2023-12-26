package com.harshilpadsala.watchlistx.state.movie_detail

import com.harshilpadsala.watchlistx.data.res.detail.ActorDetails
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetailPresenter
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.list.MovieCredits

sealed interface MovieDetailUiState {

    object Loading : MovieDetailUiState



    data class MovieDetailsSuccess(val data : MovieDetailPresenter?) : MovieDetailUiState



    data class Error(val message : String?) : MovieDetailUiState



}