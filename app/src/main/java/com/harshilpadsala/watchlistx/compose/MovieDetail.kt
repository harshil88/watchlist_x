package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.CardListComponent
import com.harshilpadsala.watchlistx.compose.components.GenresRow
import com.harshilpadsala.watchlistx.compose.components.ImagePager
import com.harshilpadsala.watchlistx.compose.components.RatingRow
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.data.res.detail.CardModel
import com.harshilpadsala.watchlistx.data.res.detail.toCardComponent
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.state.movie_detail.CreditsUiState
import com.harshilpadsala.watchlistx.state.movie_detail.FavouriteUiState
import com.harshilpadsala.watchlistx.state.movie_detail.MovieDetailUiState
import com.harshilpadsala.watchlistx.state.movie_detail.WatchlistUiState
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.MovieDetailViewModel
import utils.ErrorX
import utils.LoaderX


@Composable
fun MovieDetailRoute(
    onBackClick: () -> Unit,
    onRatingClick: (RatingArgsModel) -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val movieDetailUiState = viewModel.movieDetailStateFlow.collectAsState()
    val watchListUiState = viewModel.watchListUiState.collectAsState()
    val favouriteUiState = viewModel.favouriteUiState.collectAsState()
    val creditsUiState = viewModel.mediaCreditsStateFlow.collectAsState()
    val ratingUiState = viewModel.ratingUiState.collectAsState()

    //Todo : Add Dynamic on Movie on Rating Press

    MovieDetailScreen(
        movieDetailUiState = movieDetailUiState.value,
        watchListUiState = watchListUiState.value,
        favouriteUiState = favouriteUiState.value,
        ratingUiState = ratingUiState.value,
        creditsUiState = creditsUiState.value,
        onFavClick = viewModel::toggleFavourite,
        onWatchListClick = viewModel::toggleWatchList,
        onRatingClick = {
                      onRatingClick(
                          RatingArgsModel(
                              movieId = 901362,
                              movieName = "Trolls Band Together",
                              posterPath = "/ui4DrH1cKk2vkHshcUcGt2lKxCm.jpg",
                              isRated = false,
                              ratings = 0
                          )
                      )
        },
        onBackPress = {},
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieDetailUiState: MovieDetailUiState,
    watchListUiState: WatchlistUiState,
    favouriteUiState: FavouriteUiState,
    ratingUiState: WatchlistUiState,
    creditsUiState: CreditsUiState,
    onFavClick: (Boolean) -> Unit,
    onWatchListClick: (Boolean) -> Unit,
    onRatingClick: () -> Unit,
    onBackPress: () -> Unit,
) {
    when (movieDetailUiState) {

        is MovieDetailUiState.Loading -> LoaderX()
        is MovieDetailUiState.Error -> ErrorX()
        is MovieDetailUiState.MovieDetailsSuccess -> {

            val movieDetail = movieDetailUiState.data
            val isFavourite = movieDetailUiState.data?.movieStats?.favorite ?: false
            val isWatchListed = movieDetailUiState.data?.movieStats?.watchlist ?: false


            Scaffold(topBar = {
                TopBarX(title = movieDetail?.title ?: "", onBackPress = onBackPress, actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = "Navigate Backwards",
                            tint = if (isFavourite) Darkness.love else Darkness.light
                        )
                    }
                })
            }
            ) { paddingValues ->

                LazyColumn(
                    modifier = Modifier.padding(top = paddingValues.calculateTopPadding() + 16.dp)
                ) {
                    item {
                        ImagePager(images = movieDetail?.images ?: listOf())
                    }
                    item {

                        GenresRow(
                            genres = movieDetail?.genres ?: listOf(), modifier = Modifier.padding(
                                start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp
                            )
                        )

                        Text(
                            text = movieDetail?.overview ?: "",
                            modifier = Modifier.padding(horizontal = 24.dp),
                            style = StylesX.bodyMedium.copy(color = Darkness.grey),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )

                        WatchListButton(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                            watchListUiState = watchListUiState,
                            onWatchListClick = onWatchListClick
                        )

                        RatingRow(
                            rating = movieDetail?.voteAverage ?: 0.0,
                            users = movieDetail?.voteCount ?: 0,
                            onRatingClick = onRatingClick,
                        )
                    }

                    item {
                        when (creditsUiState) {
                            is CreditsUiState.Loading, is CreditsUiState.Error -> {}
                            is CreditsUiState.Success -> CreditsRow(credits = creditsUiState.credits?.map { it -> it.toCardComponent() }
                                ?: listOf())
                        }
                    }
                }
            }
        }
    }


}


@Composable
fun CreditsRow(credits: List<CardModel>) {
    Text(
        text = "Top Casts", modifier = Modifier.padding(
            start = 24.dp, top = 24.dp, bottom = 24.dp
        ), style = StylesX.titleLarge.copy(color = Color.Yellow), maxLines = 1
    )

    CardListComponent(cards = credits, modifier = Modifier, onCardClick = { id -> })

}


@Composable
fun WatchListButton(
    modifier: Modifier = Modifier,
    watchListUiState: WatchlistUiState,
    onWatchListClick: (Boolean) -> Unit,
) {
    ElevatedButton(
        onClick = {
            if (watchListUiState !is WatchlistUiState.Loading) {
                onWatchListClick(!watchListUiState.currentValue)
            }
        },
        modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (watchListUiState.currentValue) Darkness.stillness else Darkness.rise,
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            2.dp, Darkness.rise
        )
    ) {
        when (watchListUiState) {
            is WatchlistUiState.Loading -> LoaderX()
            is WatchlistUiState.Success -> if (watchListUiState.currentValue) AddedToWatchListContent() else NotAddedToWatchListContent()
            is WatchlistUiState.Error -> if (watchListUiState.currentValue) AddedToWatchListContent() else NotAddedToWatchListContent()
        }
    }
}

@Composable
fun NotAddedToWatchListContent() {
    Icon(
        Icons.Filled.Add, contentDescription = "Add to Watchlist Icon", tint = Darkness.stillness
    )
    Text(
        text = "Add to Watchlist", modifier = Modifier.padding(
            start = 24.dp
        ), style = StylesX.titleMedium.copy(color = Darkness.stillness), maxLines = 1
    )
}

@Composable
fun AddedToWatchListContent() {
    Icon(
        Icons.Filled.Add, contentDescription = "Add to Watchlist Icon", tint = Darkness.stillness
    )
    Text(
        text = "Add to Watchlist", modifier = Modifier.padding(
            start = 24.dp
        ), style = StylesX.titleMedium.copy(color = Darkness.stillness), maxLines = 1
    )
}

@Composable
fun ShowRatingDialog() {
    Dialog(
        onDismissRequest = { },
    ) {}
}