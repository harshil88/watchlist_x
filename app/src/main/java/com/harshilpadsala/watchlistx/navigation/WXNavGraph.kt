package com.harshilpadsala.watchlistx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.harshilpadsala.watchlistx.navigation.nav_graphs.discoverRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.favoriteRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.homeRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.movieCategory
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toMovieCategoryNav


@Composable
fun WatchListXNavigation(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = homeRoute
    ) {

        homeRoute(onMovieClick = {},
            onShowMoreClick = navController::toMovieCategoryNav,
            onRatingClick = {},
            nestedGraphBuilder = {
                movieCategory(onMovieClick = {})
            })

        discoverRoute(onMovieClick = {})

        favoriteRoute(onMovieClick = {})

        movieCategory(onMovieClick = {})

    }
}