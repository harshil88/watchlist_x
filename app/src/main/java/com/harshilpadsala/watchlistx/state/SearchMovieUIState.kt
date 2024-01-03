package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.res.list.MovieContent

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class Success(
        val recentQueries: List<MovieContent> = emptyList(),
    ) : SearchUiState

    data class Failure(
        val message: String?,
    ) : SearchUiState

}

sealed interface BaseState<T> {
    object Loading : BaseState<Nothing>

    data class Success<T>(val data: T?) : BaseState<Nothing>

    data class Error(val message: String?) : BaseState<Nothing>
}


sealed class ExtendedUiState : BaseState<MovieDetailSuccess>