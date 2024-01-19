package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.MoviesList
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenErrorX
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenLoaderX
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.constants.isScrolledToEnd
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.FavouriteUiState
import com.harshilpadsala.watchlistx.vm.FavouriteViewModel
import utils.ErrorX
import utils.LoaderX

@Composable
fun FavouriteRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: FavouriteViewModel = hiltViewModel(),
) {

    val favouriteListState = rememberLazyListState()
    val wishListState = rememberLazyListState()
    val favouriteUiState = rememberUpdatedState(newValue = viewModel.favouriteUiState)

    val endOfFavouriteListReached by remember {
        derivedStateOf {
            favouriteListState.isScrolledToEnd()
        }
    }

    val endOfWishListReached by remember {
        derivedStateOf {
            wishListState.isScrolledToEnd()
        }
    }

    if (endOfFavouriteListReached
        && favouriteListState.isScrollInProgress
        && favouriteUiState.value.isFavouriteLoading == false &&
        !favouriteUiState.value.hasReachedEndForFavourites) {
        viewModel.nextPage(FavouriteType.Favourite)
    }

    if (endOfWishListReached
        && wishListState.isScrollInProgress
        && favouriteUiState.value.isWishlistLoading == false &&
        !favouriteUiState.value.hasReachedEndForWatchlist) {
        viewModel.nextPage(FavouriteType.Watchlist)
    }

    FavouriteScreen(favouriteUiState = favouriteUiState.value,
        favouriteListState = favouriteListState,
        wishListState = wishListState,
        onMovieClick = onMovieClick,
        onError = viewModel::reset,
        onTabChange = viewModel::changeTab,
        )
}


@Composable
fun FavouriteScreen(
    favouriteUiState: FavouriteUiState,
    favouriteListState: LazyListState,
    wishListState: LazyListState,
    onError : (FavouriteType) -> Unit,
    onTabChange: (FavouriteType) -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    Column {
        FavouriteTabRow(
            modifier = Modifier.padding(vertical = 16.dp),
            onTabChange = onTabChange,
        )

        if(favouriteUiState.isFavouriteLoading == null &&
            favouriteUiState.currentTabType == FavouriteType.Favourite){
            FullScreenLoaderX()
        }

        else if(favouriteUiState.isWishlistLoading == null &&
            favouriteUiState.currentTabType == FavouriteType.Watchlist){
            FullScreenLoaderX()
        }

        else if(favouriteUiState.errorForFav !=null &&
            favouriteUiState.currentTabType == FavouriteType.Favourite
            ){
            FullScreenErrorX(
                text = favouriteUiState.errorForFav!!,
                onClick = {
                    onError(FavouriteType.Favourite)
                }
            )
        }

        else if(favouriteUiState.errorForWishlist != null &&
            favouriteUiState.currentTabType == FavouriteType.Watchlist){
            FullScreenErrorX(
                text = favouriteUiState.errorForWishlist!!,
                onClick = {
                    onError(FavouriteType.Watchlist)
                }
            )
        }

        else if (favouriteUiState.favouriteMovies != null &&
            favouriteUiState.currentTabType == FavouriteType.Favourite) {
            FavouriteMoviesList(
                favouriteUiState = favouriteUiState,
                favouriteListState = favouriteListState,
                onMovieClick = onMovieClick,
            )
        }

        else if (favouriteUiState.watchlistMovies != null&& favouriteUiState.currentTabType == FavouriteType.Watchlist) {
            WishListMovieList(
                favouriteUiState = favouriteUiState,
                wishListState = wishListState,
                onMovieClick = onMovieClick,
            )
        }
    }
}

@Composable
fun FavouriteMoviesList(
    favouriteUiState: FavouriteUiState,
    favouriteListState: LazyListState,
    onMovieClick: (Int) -> Unit,
) {
    MoviesList(
        modifier = Modifier.padding(horizontal = 20.dp,),
        hasReachedEnd = favouriteUiState.hasReachedEndForFavourites,
        movies = favouriteUiState.favouriteMovies!!,
        lazyListState = favouriteListState,
        onItemClick = onMovieClick,
    )
}

@Composable
fun WishListMovieList(
    favouriteUiState: FavouriteUiState,
    wishListState: LazyListState,
    onMovieClick: (Int) -> Unit,
) {
    MoviesList(
        modifier = Modifier.padding(horizontal = 20.dp,),
        hasReachedEnd = favouriteUiState.hasReachedEndForWatchlist,
        movies = favouriteUiState.watchlistMovies!!,
        lazyListState = wishListState,
        onItemClick = onMovieClick,
    )
}


@Composable
fun FavouriteTabRow(
    modifier: Modifier = Modifier,
    onTabChange: (FavouriteType) -> Unit,
) {

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    TabRow(modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Darkness.water,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .padding(top = 8.dp), color = Darkness.rise
            )
        }) {
        FavouriteType.values().mapIndexed { index, tab ->
            Tab(modifier = Modifier.padding(vertical = 8.dp),
                selected = index == selectedTabIndex,
                onClick = {
                    if(selectedTabIndex!=index){
                        selectedTabIndex = index
                        onTabChange(tab)
                    }
                }) {
                Text(text = tab.name, style = StylesX.labelLarge)
            }
        }
    }
}
