package com.harshilpadsala.watchlistx.navigation.nav_graphs

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.MovieCategoryRoute
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.navigation.ArgumentsX

internal const val movieCategoryRoute = "MovieCategoryRoute"

fun NavController.toMovieCategoryNav(movieCategory: MovieCategory) {
    this.navigate("${movieCategoryRoute}/${movieCategory}")
}

fun NavGraphBuilder.movieCategory(onMovieClick: (Int) -> Unit) {
    composable(
        route = "${movieCategoryRoute}/{${ArgumentsX.movieId}}",
        arguments = listOf(
            navArgument(
                name = ArgumentsX.movieId,
            ){
                type = NavType.StringType
                defaultValue = null
                nullable = true
            }
        )
    ) {
        MovieCategoryRoute(
            onMovieClick = onMovieClick
        )
    }
}