package com.harshilpadsala.watchlistx.state.movie_detail

import com.harshilpadsala.watchlistx.data.res.detail.ActorDetails

sealed interface CreditsUiState {

    object Loading : CreditsUiState

    data class Success(val credits : List<ActorDetails>?) : CreditsUiState

    data class Error(val message : String) : CreditsUiState

}