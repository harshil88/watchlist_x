package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.MoviesList
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenErrorX
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenLoaderX
import com.harshilpadsala.watchlistx.constants.isScrolledToEnd
import com.harshilpadsala.watchlistx.vm.MovieCategoryUiState
import com.harshilpadsala.watchlistx.vm.MovieCategoryViewModel

@Composable
fun MovieCategoryRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieCategoryViewModel = hiltViewModel(),
) {
    val uiState = rememberUpdatedState(newValue = viewModel.movieCategoryUiState)

    val listState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    if (endOfListReached && listState.isScrollInProgress && uiState.value.isLoading == false && !uiState.value.hasReachedEnd) {
        viewModel.nextPage()
    }

    MovieCategoryScreen(
        uiState = uiState.value,
        listState = listState,
        onMovieClick = onMovieClick,
        onError = {},
    )
}

@Composable
fun MovieCategoryScreen(
    uiState: MovieCategoryUiState,
    listState: LazyListState,
    onMovieClick: (Int) -> Unit,
    onError: () -> Unit,
) {

    if (uiState.isLoading == null) {
        FullScreenLoaderX()
    } else if (uiState.movies != null) {
        MoviesCategoryList(uiState = uiState, listState = listState, onMovieClick = onMovieClick)
    } else if (!uiState.error.isNullOrEmpty()) {
        FullScreenErrorX(text = uiState.error!!, onClick = onError)
    }

}

@Composable
fun MoviesCategoryList(
    uiState: MovieCategoryUiState,
    listState: LazyListState,
    onMovieClick: (Int) -> Unit,
) {
    MoviesList(
        modifier = Modifier.padding(horizontal = 20.dp),
        hasReachedEnd = uiState.hasReachedEnd,
        movies = uiState.movies!!,
        lazyListState = listState,
        onItemClick = onMovieClick,
    )
}


