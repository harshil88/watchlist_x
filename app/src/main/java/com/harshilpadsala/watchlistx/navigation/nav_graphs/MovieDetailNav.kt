package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.MovieDetailRoute
import com.harshilpadsala.watchlistx.navigation.ArgumentsX

const val movieDetailRoute = "movieDetailRoute"

fun NavController.toMovieDetail(movieId : Int, navOptions: NavOptions? = null) {
    this.navigate("$movieDetailRoute/${movieId}", navOptions)
}

fun NavGraphBuilder.movieDetailRoute(
    onBackClick: () -> Unit,
    onRatingClick: (String) -> Unit,
) {
    composable(
        route = "${movieDetailRoute}/{${ArgumentsX.movieId}}",
        arguments = listOf(navArgument(
            name = ArgumentsX.movieId,
        ) {
            type = NavType.IntType
            defaultValue = 0
            nullable = false
        }),
    ) {
        MovieDetailRoute(
            onBackClick = onBackClick,
            onRatingClick = {},
        )
    }
}