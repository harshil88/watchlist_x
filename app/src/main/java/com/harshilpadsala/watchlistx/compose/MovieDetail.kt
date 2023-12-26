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

    val movieDetailUiState = viewModel.movieDetailStateFlow.collectAsState()
    val watchListUiState = viewModel.watchListUiState.collectAsState()
    val ratingUiState = viewModel.ratingUiState.collectAsState()

    MovieDetailScreen(
        movieDetailUiState = movieDetailUiState.value,
        watchListUiState = watchListUiState.value,
        ratingUiState = ratingUiState.value,
        creditsUiState = CreditsUiState.Loading,
        onFavClick = { _, _ -> },
        onWatchListClick = { _, _ -> },
        onAddRatingClick = { movieId, add -> },
        onDeleteRatingClick = { movieId -> })
}

@Composable
fun MovieDetailScreen(
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
        Text(text = movieDetailUiState.toString())


    }
}