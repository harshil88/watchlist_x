package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.CardListComponent
import com.harshilpadsala.watchlistx.compose.components.GenresRow
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenLoaderX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.res.detail.CardModel
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.HomeUiState
import com.harshilpadsala.watchlistx.vm.HomeViewModel


@Composable
fun HomeRoute(
    onMediaClick: (MediaType, Int) -> Unit,
    onGenreClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState = rememberUpdatedState(newValue = viewModel.uiState.value)

    HomeScreen(uiState = uiState.value)
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
) {
    if (uiState.loading == true) {
        FullScreenLoaderX()
    } else {
        MediaList(uiState = uiState)
    }


}

@Composable
fun MediaList(uiState: HomeUiState) {


    LazyColumn {


        if (!uiState.nowPlayingMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Now Playing",
                    cards = uiState.nowPlayingMovieList!!,
                    onCardClick = {},
                    onShowMoreClick = {})
            }
        }

        if (!uiState.popularMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Popular",
                    cards = uiState.popularMovieList!!,
                    onCardClick = {},
                    onShowMoreClick = {})
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
                    onCardClick = {},
                    onShowMoreClick = {})
            }
        }

        if (!uiState.upcomingMovieList.isNullOrEmpty()) {
            item {
                ListContent(
                    title = "Upcoming",
                    cards = uiState.upcomingMovieList!!,
                    onCardClick = {
                    },
                    onShowMoreClick = {})
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
        CardListComponent(cards = cards, modifier = modifier, onCardClick = onCardClick)
    }
}


