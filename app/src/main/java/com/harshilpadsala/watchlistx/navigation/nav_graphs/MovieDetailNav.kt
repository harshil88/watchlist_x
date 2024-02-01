package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.MovieDetailRoute
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.navigation.ArgumentsX

const val movieDetailRoute = "movieDetailRoute"

fun NavController.toMovieDetail(movieId : Int, navOptions: NavOptions? = null) {
    this.navigate("$movieDetailRoute/${movieId}", navOptions)
}

fun NavGraphBuilder.movieDetailRoute(
    onBackClick: () -> Unit,
    onRatingClick: (RatingArgsModel) -> Unit,
) {
    composable(
        route = "${movieDetailRoute}/{${ArgumentsX.movieId}}",
        arguments = listOf(
                navArgument(
            name = ArgumentsX.movieId,
        ) {
            type = NavType.IntType
            defaultValue = 0
            nullable = false
        } ,),
    ) {
        MovieDetailRoute(
            onRatingClick = onRatingClick,
            onBackClick = onBackClick
        )
    }
}