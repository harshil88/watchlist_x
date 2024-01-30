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

val homeRoute = WXNavItem.HOME.name

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeRoute(
    onMovieClick: (Int) -> Unit,
    onRatingClick: (String) -> Unit,
    onShowMoreClick: (MovieCategory) -> Unit,
    nestedGraphBuilder: NavGraphBuilder.() -> Unit,
) {
    composable(homeRoute) {
        HomeRoute(
            onMediaClick = onMovieClick,
            onRatingClick = {
                ratingArgsModel ->
                val convertedToJson = Gson().toJson(
                    ratingArgsModel
                )
                val encodedUri = Uri.encode(convertedToJson)
                onRatingClick("")
            },
            onShowMoreClick = onShowMoreClick,
        )
        nestedGraphBuilder()
    }
}


