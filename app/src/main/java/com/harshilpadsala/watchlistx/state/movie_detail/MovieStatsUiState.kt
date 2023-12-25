package com.harshilpadsala.watchlistx.state.movie_detail

import com.harshilpadsala.watchlistx.data.res.detail.MovieStats

sealed interface MovieStatsUiState {
    object Loading : MovieStatsUiState
    data class Success(
        val movieStats: MovieStats? = null
    ) : MovieStatsUiState

    data class Failure(
        val message : String?,
    ) : MovieStatsUiState

}