package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.state.WatchListUiState
import com.harshilpadsala.watchlistx.state.movie_detail.CreditsUiState
import com.harshilpadsala.watchlistx.state.movie_detail.MovieDetailUiState
import com.harshilpadsala.watchlistx.state.movie_detail.MovieStatsUiState
import com.harshilpadsala.watchlistx.state.movie_detail.RatingUiState
import com.harshilpadsala.watchlistx.vm.MovieDetailViewModel


@Composable
fun MovieDetailRoute(
    onBackPress: () -> Unit, viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val movieStatsUiState = viewModel.mediaStatsStateflow.collectAsState()
    val movieDetailUiState = viewModel.mediaDetailStateFlow.collectAsState()
    val watchListUiState = viewModel.watchListUiState.collectAsState()

    MovieDetailScreen(
        movieStatsUiState = movieStatsUiState.value,
        movieDetailUiState = movieDetailUiState.value,
        watchListUiState = WatchListUiState.Loading,
        ratingUiState = RatingUiState.Loading,
        creditsUiState = CreditsUiState.Loading,
        onFavClick = { _, _ -> },
        onWatchListClick = { _, _ -> },
        onAddRatingClick = { movieId, add -> },
        onDeleteRatingClick = { movieId -> })
}

@Composable
fun MovieDetailScreen(
    movieStatsUiState: MovieStatsUiState,
    movieDetailUiState: MovieDetailUiState,
    watchListUiState: WatchListUiState,
    ratingUiState: RatingUiState,
    creditsUiState: CreditsUiState,
    onFavClick: (Int, Boolean) -> Unit,
    onWatchListClick: (Int, Boolean) -> Unit,
    onAddRatingClick: (Int, Boolean) -> Unit,
    onDeleteRatingClick: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = movieStatsUiState.toString())
        Text(text = movieDetailUiState.toString())


    }
}