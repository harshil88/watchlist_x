package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetailPresenter
import com.harshilpadsala.watchlistx.state.RatingUiState
import com.harshilpadsala.watchlistx.vm.Rated
import com.harshilpadsala.watchlistx.vm.RatingViewModel

@Composable
fun RatingRoute(
    isRated : Boolean,
    ratings : Float?,
    onBackPress : () -> Unit,
    viewModel: RatingViewModel = hiltViewModel(),
) {

    val ratingUiState = viewModel.ratingState

    RatingScreen(ratingUiState = ratingUiState.value)
}


@Composable
fun RatingScreen(
     ratingUiState: Rated
) {

}