package com.harshilpadsala.watchlistx.state.movie_detail

sealed interface WatchlistUiState{


    object Loading : WatchlistUiState

    data class FavouriteSuccess(val message : String?) : WatchlistUiState

    data class WatchlistSuccess(val message : String?) : WatchlistUiState

    data class Error(val message : String?) : WatchlistUiState


}