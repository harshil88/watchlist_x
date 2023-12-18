package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.res.model.ListItemXData

sealed interface DiscoverMovieUiState {

    object Initial : DiscoverMovieUiState
    object Loading : DiscoverMovieUiState


    data class SuccessUiState(
        val movies: List<ListItemXData>,
        var currentPage: Int = 1,
        var hasReachedEnd: Boolean = false
    ) : DiscoverMovieUiState

    data class PopularMovieFailureUiState(val message: String) : DiscoverMovieUiState
}

