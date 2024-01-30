package com.harshilpadsala.watchlistx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.harshilpadsala.watchlistx.navigation.nav_graphs.discoverRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.favoriteRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.homeRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.movieCategory
import com.harshilpadsala.watchlistx.navigation.nav_graphs.movieDetailRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toMovieCategory
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toMovieDetail


@Composable
fun WatchListXNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = homeRoute
    ) {

        homeRoute(
            onMovieClick = {},
            onShowMoreClick = navController::toMovieCategory,
            onRatingClick = {},
            nestedGraphBuilder = {
                movieCategory(onMovieClick = navController::toMovieDetail)
            })

        discoverRoute(onMovieClick = {})

        favoriteRoute(onMovieClick = {})

        movieCategory(onMovieClick = navController::toMovieDetail)

        movieDetailRoute(
            onBackClick = {},
            onRatingClick = {}
        )
    }
}