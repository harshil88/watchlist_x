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

fun NavController.toMovieCategory(movieCategory: MovieCategory) {
    this.navigate("${movieCategoryRoute}/${movieCategory}")
}

fun NavGraphBuilder.movieCategory(onMovieClick: (Int) -> Unit) {
    composable(
        route = "${movieCategoryRoute}/{${ArgumentsX.movieCategory}}",
        arguments = listOf(
            navArgument(
                name = ArgumentsX.movieCategory,
            ){
                type = NavType.StringType
                defaultValue = MovieCategory.NowPlaying.toString()
                nullable = false
            }
        )
    ) {
        MovieCategoryRoute(
            onMovieClick = onMovieClick
        )
    }
}