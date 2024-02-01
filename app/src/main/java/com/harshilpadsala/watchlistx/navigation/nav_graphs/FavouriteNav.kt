package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.harshilpadsala.watchlistx.compose.FavouriteRoute
import com.harshilpadsala.watchlistx.constants.WXNavItem

val favoriteRoute = WXNavItem.FAVOURITE.name

fun NavGraphBuilder.favoriteRoute(
    onMovieClick: (Int) -> Unit,
) {
    composable(favoriteRoute) {
        FavouriteRoute(
            onMovieClick = onMovieClick,
        )
    }
}