package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.MoviesList
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenLoaderX
import com.harshilpadsala.watchlistx.constants.isScrolledToEnd
import com.harshilpadsala.watchlistx.data.res.model.FilterParams
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.DiscoverUiState
import com.harshilpadsala.watchlistx.vm.DiscoverVM

//todo : Learn when and why to use rememberUpdatedState

//todo : Reduce state calls is at all possible

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DiscoverRoute(
    onMovieClick: (Int) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    filterParams: FilterParams? = null,
    viewModel: DiscoverVM = hiltViewModel()

) {

    val uiState = rememberUpdatedState(newValue = viewModel.discoverUiState.value)

    val lazyListState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }

    if (endOfListReached
        && lazyListState.isScrollInProgress
        && uiState.value.isLoading == false &&
        !uiState.value.hasReachedEnd
    ) {
        viewModel.nextPage()
    }

    DiscoverScreen(
        uiState = uiState.value,
        lazyListState = lazyListState,
        onMovieClick = onMovieClick,
        onFilterListClick = onFilterClick,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiscoverScreen(
    uiState: DiscoverUiState,
    onFilterListClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    lazyListState: LazyListState,
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
                text = "Discover Movies", style = StylesX.titleLarge.copy(color = Darkness.rise)
            )
            IconButton(onClick = onFilterListClick) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Show More Icon",
                    tint = Darkness.light
                )
            }
        }

        if (uiState.isLoading == null) {
            FullScreenLoaderX(
                modifier = Modifier
            )
        } else if (uiState.movies != null) {
            MoviesList(
                hasReachedEnd = uiState.hasReachedEnd,
                modifier = Modifier.padding(horizontal = 20.dp,),
                movies = uiState.movies,
                lazyListState = lazyListState,
                onItemClick = onMovieClick,
            )
            
            Spacer(modifier = Modifier.padding(bottom = 72.dp))
        }
    }
}






