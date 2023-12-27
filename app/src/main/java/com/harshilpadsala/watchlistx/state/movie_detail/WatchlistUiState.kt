package com.harshilpadsala.watchlistx.state.movie_detail

sealed class WatchlistUiState(open val currentValue : Boolean){

    object Loading : WatchlistUiState(false)

    data class Success( val successValue: Boolean, ) : WatchlistUiState(successValue)

    data class Error( val previousValue: Boolean ) : WatchlistUiState(previousValue)



}

sealed interface FavouriteUiState{


    object Loading : FavouriteUiState

    object AddedToFav : FavouriteUiState

    object RemovedFromFav : FavouriteUiState

    data class Error(val message : String?) : FavouriteUiState


}