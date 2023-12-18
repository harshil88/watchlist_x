package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.res.list.Movie

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class Success(
        val recentQueries: List<Movie> = emptyList(),
    ) : SearchUiState

    data class Failure(
        val message : String?,
    ) : SearchUiState

}