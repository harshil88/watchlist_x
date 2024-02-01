package com.harshilpadsala.watchlistx.navigation.nav_graphs

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.harshilpadsala.watchlistx.compose.HomeRoute
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.constants.WXNavItem
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel

val homeRoute = WXNavItem.HOME.name

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

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


