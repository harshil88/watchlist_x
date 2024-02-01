package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.harshilpadsala.watchlistx.compose.HomeRoute
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.constants.WXNavItem
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel

val homeRoute = WXNavItem.HOME.name

fun NavGraphBuilder.homeRoute(
    onMovieClick: (Int) -> Unit,
    onRatingClick: (RatingArgsModel) -> Unit,
    onShowMoreClick: (MovieCategory) -> Unit,
) {
    composable(homeRoute) {
        HomeRoute(
            onMediaClick = onMovieClick,
            onRatingClick = onRatingClick,
            onShowMoreClick = onShowMoreClick,
        )
    }
}


