package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.harshilpadsala.watchlistx.compose.DiscoverRoute
import com.harshilpadsala.watchlistx.constants.WXNavItem

val discoverRoute = WXNavItem.DISCOVER.name

fun NavController.navigateToDiscover(navOptions: NavOptions? = null) {
    this.navigate(discoverRoute, navOptions)
}

fun NavGraphBuilder.discoverRoute(
    onMovieClick: (Int) -> Unit,
) {
    composable(discoverRoute) {
        DiscoverRoute(
            onMovieClick = onMovieClick,
            onFilterClick = { /*TODO*/ },
        )
    }
}

