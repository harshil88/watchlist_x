package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.CardListComponent
import com.harshilpadsala.watchlistx.compose.components.GenresRow
import com.harshilpadsala.watchlistx.compose.components.base_x.AsyncImageX
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenLoaderX
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.model.CardModel
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.data.res.model.toRatingArgsModel
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.HomeUiState
import com.harshilpadsala.watchlistx.vm.HomeViewModel
import com.harshilpadsala.watchlistx.vm.MovieStatsUiState
import kotlinx.coroutines.launch
import utils.ToastX

//todo : Question -> A deeper understanding of scopes

//todo : Furthur Optimization -> For Different Types Of Movie API Calls Use Enums

//todo : NUNI -> While Using Swipe To Refresh, The First List Of Now Playing gets scrolled to first item, not happening with other


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRoute(
    onMediaClick: (Int) -> Unit,
    onGenreClick: (Int) -> Unit,
    onRatingClick: (RatingArgsModel) -> Unit,
    onShowMoreClick: (MovieList) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState = rememberUpdatedState(newValue = viewModel.uiState.value)
    val movieStatsUiState = rememberUpdatedState(newValue = viewModel.movieStatsUiState.value)
    val scope = rememberCoroutineScope()

    val refreshingState = rememberPullRefreshState(
        refreshing = uiState.value.refreshing, onRefresh = viewModel::onRefresh
    )

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    if (!sheetState.isVisible) {
        viewModel.resetMovieStats()
    }

    movieStatsUiState.value.successMessage.let {
        if (it != null) {
            ToastX(message = it)
        }
    }

    movieStatsUiState.value.errorMessage.let {
        if (it != null) {
            ToastX(message = it)
        }
    }

    HomeScreen(
        uiState = uiState.value,
        movieStatsUiState = movieStatsUiState.value,
        sheetState = sheetState,
        refreshingState = refreshingState,
        onFavouriteClick = viewModel::favourite,
        onWatchListClick = viewModel::wishList,
        onRatingClick = { ratingArgsModel ->
            scope.launch {
                sheetState.hide()
            }
            onRatingClick(ratingArgsModel)
        },
        onCardClick = onMediaClick,
        onShowMoreClick = onShowMoreClick,
        onLongCardClick = { movieDetails ->
            scope.launch {
                sheetState.show()
                viewModel.movieStats(movieDetails)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    movieStatsUiState: MovieStatsUiState,
    sheetState: ModalBottomSheetState,
    refreshingState: PullRefreshState,
    onCardClick: (Int) -> Unit,
    onLongCardClick: (CardModel) -> Unit,
    onFavouriteClick: (Boolean, Int) -> Unit,
    onWatchListClick: (Boolean, Int) -> Unit,
    onRatingClick: (RatingArgsModel) -> Unit,
    onShowMoreClick: (MovieList) -> Unit
) {
    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = {
        if (movieStatsUiState.loading == true) {
            FullScreenLoaderX(
                modifier = Modifier
                    .fillMaxHeight(0.5F)
                    .background(Darkness.night)
            )
        } else if (movieStatsUiState.movieStats != null) {
            MovieStatsSheetContent(
                isWatchListed = movieStatsUiState.movieStats?.watchlist ?: false,
                isFavourite = movieStatsUiState.movieStats?.favorite ?: false,
                ratings = movieStatsUiState.movieStats?.ratings?.value,
                cardModel = movieStatsUiState.selectedMovieDetail,
                onFavouriteClick = onFavouriteClick,
                onWatchListClick = onWatchListClick,
                onRatingClick = onRatingClick
            )
        }
    }, content = {
        if (uiState.loading == true) {
            FullScreenLoaderX()
        } else {
            Box(
                modifier = Modifier
                    .pullRefresh(refreshingState)
                    .fillMaxSize()
            ) {
                MediaList(
                    uiState = uiState,
                    onCardClick = onCardClick,
                    onLongCardClick = onLongCardClick,
                    onShowMoreClick = onShowMoreClick
                )
                PullRefreshIndicator(
                    uiState.refreshing, refreshingState, Modifier.align(Alignment.TopCenter)
                )
            }
        }
    })
}


@Composable
fun MediaList(
    uiState: HomeUiState,
    onCardClick: (Int) -> Unit,
    onLongCardClick: (CardModel) -> Unit,
    onShowMoreClick: (MovieList) -> Unit
) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {


        if (!uiState.nowPlayingMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Now Playing",
                    cards = uiState.nowPlayingMovieList!!,
                    onCardClick = onCardClick,
                    onShowMoreClick = {
                        onShowMoreClick(MovieList.NowPlaying)
                    },
                    onLongCardClick = onLongCardClick
                )
            }
        }

        if (!uiState.popularMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Popular",
                    cards = uiState.popularMovieList!!,
                    onCardClick = onCardClick,
                    onShowMoreClick = { onShowMoreClick(MovieList.Popular) },
                    onLongCardClick = onLongCardClick
                )
            }
        }

        if (!uiState.genres.isNullOrEmpty()) {
            item {
                Column(
                    modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 8.dp)
                ) {
                    Text(
                        text = "Get By Genres",
                        style = StylesX.titleLarge.copy(color = Darkness.rise)
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    GenresRow(genres = uiState.genres!!)
                }
            }
        }


        if (!uiState.topRatedMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Top Rated",
                    cards = uiState.topRatedMovieList!!,
                    onCardClick = onCardClick,
                    onShowMoreClick = {
                        onShowMoreClick(MovieList.TopRated)
                    },
                    onLongCardClick = onLongCardClick
                )
            }
        }

        if (!uiState.upcomingMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Upcoming",
                    cards = uiState.upcomingMovieList!!,
                    onCardClick = onCardClick,
                    onShowMoreClick = {
                        onShowMoreClick(MovieList.Upcoming)
                    },
                    onLongCardClick = onLongCardClick
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(top = 24.dp))
        }
    }
}


@Composable
fun ListContent(
    title: String,
    cards: List<CardModel>,
    modifier: Modifier = Modifier,
    onCardClick: (Int) -> Unit,
    onLongCardClick: (CardModel) -> Unit,
    onShowMoreClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 16.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title, style = StylesX.titleLarge.copy(color = Darkness.rise)
            )
            IconButton(onClick = onShowMoreClick) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Show More Icon",
                    tint = Darkness.light
                )
            }
        }

        Spacer(modifier = Modifier.padding(top = 16.dp))
        CardListComponent(
            cards = cards,
            modifier = modifier,
            onCardClick = { cardModel -> onCardClick(cardModel.id ?: 0) },
            onLongCardClick = onLongCardClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieStatsSheetContent(
    isFavourite: Boolean,
    isWatchListed: Boolean,
    cardModel: CardModel?,
    ratings: Double?,
    onFavouriteClick: (Boolean, Int) -> Unit,
    onWatchListClick: (Boolean, Int) -> Unit,
    onRatingClick: (RatingArgsModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.5F)
            .background(color = Darkness.night)
    ) {

        Spacer(modifier = Modifier.padding(top = 16.dp))

        ListItem(
            text = {
            Text(
                text = cardModel?.title ?: "", style = StylesX.titleLarge,
            )
        }, icon = {
            AsyncImageX(
                imageSrc = cardModel?.imageUri ?: "", modifier = Modifier
                    .height(72.dp)
                    .width(40.dp)
            )
        }
        )

        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 20.dp)
                .height(2.dp)
                .background(Darkness.grey)
        )

        ListItem(
            modifier = Modifier
                .clickable {
                    onWatchListClick(!isWatchListed, cardModel?.id ?: 0)
                }
                .background(color = Darkness.night),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    tint = if (isWatchListed) Darkness.rise else Darkness.grey,
                    contentDescription = "Watchlist Toggle",
                )
            },
            text = {
                Text(
                    text = if (isWatchListed) "Remove From Watchlist" else "Add To Watchlist",
                    style = StylesX.labelMedium.copy(color = Darkness.grey)
                )
            },
        )

        ListItem(
            modifier = Modifier
                .clickable {
                    onFavouriteClick(!isFavourite, cardModel?.id ?: 0)
                }
                .background(color = Darkness.night),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    tint = if (isFavourite) Darkness.love else Darkness.grey,
                    contentDescription = "Watchlist Toggle",
                )
            },
            text = {
                Text(
                    text = if (isFavourite) "Remove From Favourite" else "Add To Favourite",
                    style = StylesX.labelMedium.copy(color = Darkness.grey)
                )
            },
        )

        ListItem(
            modifier = Modifier.clickable {

                if (cardModel != null) {
                    onRatingClick(cardModel.toRatingArgsModel(value = ratings))
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star, contentDescription = "Watchlist Toggle",
                    tint = if (ratings != null) Darkness.rise else Darkness.grey,
                )
            },
            text = {
                Text(
                    text = if (ratings != null) "Your Ratings : $ratings" else "Give Ratings",
                    style = StylesX.labelMedium.copy(color = Darkness.grey)
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MovieStatsSheetContentPreview() {
    Column {
        ListItem(
            modifier = Modifier.clickable {

            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Bookmark, contentDescription = "Watchlist Toggle"
                )
            },
            text = { Text(text = "Add To Watchlist") },

            )
        ListItem(
            modifier = Modifier.clickable {

            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite, contentDescription = "Watchlist Toggle"
                )
            },
            text = { Text(text = "Add To Favourites") },
        )
        ListItem(
            modifier = Modifier.clickable {

            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star, contentDescription = "Watchlist Toggle"
                )
            },
            text = { Text(text = "Rating") },
        )
        ListItem(
            modifier = Modifier.clickable {

            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star, contentDescription = "Watchlist Toggle"
                )
            },
            text = { Text(text = "Rating") },
        )

    }
}


