package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.harshilpadsala.watchlistx.compose.DiscoverRoute
import com.harshilpadsala.watchlistx.constants.WXNavItem
import com.harshilpadsala.watchlistx.constants.parseData
import com.harshilpadsala.watchlistx.data.res.model.FilterParams
import com.harshilpadsala.watchlistx.navigation.ArgumentsX

val discoverRoute = WXNavItem.DISCOVER.name

fun NavController.navigateToDiscover(navOptions: NavOptions? = null) {
    this.navigate(discoverRoute, navOptions)
}

fun NavGraphBuilder.discoverRoute(
    onFilterClick : () -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    composable(discoverRoute) {
        entry ->
        DiscoverRoute(
            filterParams = entry.savedStateHandle.get<String>(ArgumentsX.filter)?.parseData<FilterParams>(),
            onMovieClick = onMovieClick,
            onFilterClick = onFilterClick,
        )
    }
}

