package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData

sealed interface WatchListUiState {


    object Loading : WatchListUiState


    data class Success(
        val message: String,
    ) : WatchListUiState

    data class Error(val message: String) : WatchListUiState
}