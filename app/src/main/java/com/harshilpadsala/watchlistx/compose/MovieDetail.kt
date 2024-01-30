package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.CardListComponent
import com.harshilpadsala.watchlistx.compose.components.GenresRow
import com.harshilpadsala.watchlistx.compose.components.ImagePager
import com.harshilpadsala.watchlistx.compose.components.RatingRow
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.WXToggleIconButton
import com.harshilpadsala.watchlistx.data.res.detail.toCardComponent
import com.harshilpadsala.watchlistx.data.res.model.CardModel
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


//Todo : Add Dynamic on Movie on Rating Press

//Todo : Learn more about flexible row layouts

//Todo : Bug About Row Arrangement Of Action Buttons


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

    MovieDetailScreen(
        movieDetailUiState = movieDetailUiState.value,
        watchListUiState = watchListUiState.value,
        favouriteUiState = favouriteUiState.value,
        ratingUiState = ratingUiState.value,
        creditsUiState = creditsUiState.value,
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
        onListClick = {},
        onFavouriteClick = {},
        onWatchListClick = viewModel::toggleWatchList,
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
    onFavouriteClick: (Boolean) -> Unit,
    onWatchListClick: (Boolean) -> Unit,
    onListClick: (Boolean) -> Unit,
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


            Scaffold(
                topBar = {
                    TopBarX(
                        title = movieDetail?.title ?: "",
                        onBackPress = onBackPress,
                    )
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

                        ActionButtonsRow(
                            isFavourite = movieDetail?.movieStats?.favorite ?: false,
                            isWatchListed = movieDetail?.movieStats?.watchlist ?: false,
                            isListed = true,
                            onWatchListClick = onWatchListClick,
                            onFavouriteClick = onFavouriteClick,
                            onListClick = onListClick,
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
fun ActionButtonsRow(
    isFavourite: Boolean,
    isWatchListed: Boolean,
    isListed: Boolean,
    onFavouriteClick: (Boolean) -> Unit,
    onWatchListClick: (Boolean) -> Unit,
    onListClick: (Boolean) -> Unit


) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(
                horizontal = 20.dp, vertical = 20.dp
            )
            .fillMaxWidth()
    ) {

        val maxWidthModifier = Modifier.weight(1F)

        WXToggleIconButton(
            modifier = maxWidthModifier,
            state = isWatchListed,
            icon = Icons.Filled.Bookmark,
            onStateColor = Darkness.rise,
            onClick = {
                onWatchListClick(!isWatchListed)
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        WXToggleIconButton(
            modifier = maxWidthModifier,

            state = isFavourite,
            icon = Icons.Filled.Favorite,
            onStateColor = Darkness.love,
            onClick = {
                onFavouriteClick(!isFavourite)
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        WXToggleIconButton(
            state = isListed,
            icon = Icons.Filled.List,
            onStateColor = Darkness.life,
            onClick = {
                onListClick(!isListed)
            }
        )

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
fun ShowRatingDialog() {
    Dialog(
        onDismissRequest = { },
    ) {}
}


@Preview
@Composable
fun ActionButtonsRowPreview() {
    ActionButtonsRow(
        isFavourite = true,
        isWatchListed = false,
        isListed = false,
        onFavouriteClick = {},
        onListClick = {},
        onWatchListClick = {},
    )
}


