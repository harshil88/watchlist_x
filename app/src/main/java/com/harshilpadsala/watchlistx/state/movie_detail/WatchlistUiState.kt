package com.harshilpadsala.watchlistx.state.movie_detail

sealed interface WatchlistUiState{


    object Loading : WatchlistUiState

    object AddedToFav : WatchlistUiState

    object RemovedFromFav : WatchlistUiState

    data class Error(val message : String?) : WatchlistUiState


}

sealed interface FavouriteUiState{


    object Loading : FavouriteUiState

    object AddedToFav : FavouriteUiState

    object RemovedFromFav : FavouriteUiState

    data class Error(val message : String?) : FavouriteUiState


}