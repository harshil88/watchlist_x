package com.harshilpadsala.watchlistx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.harshilpadsala.watchlistx.navigation.nav_graphs.discoverRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.favoriteRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.filterRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.homeRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.movieCategory
import com.harshilpadsala.watchlistx.navigation.nav_graphs.movieDetailRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.ratingRoute
import com.harshilpadsala.watchlistx.navigation.nav_graphs.saveFilters
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toFilter
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toMovieCategory
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toMovieDetail
import com.harshilpadsala.watchlistx.navigation.nav_graphs.toRating


@Composable
fun WatchListXNavigation(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = homeRoute
    ) {

        homeRoute(
            onMovieClick = navController::toMovieDetail,
            onShowMoreClick = navController::toMovieCategory,
            onRatingClick = navController::toRating,
        )

        discoverRoute(
            onMovieClick = navController::toMovieDetail, onFilterClick = navController::toFilter
        )

        favoriteRoute(onMovieClick = navController::toMovieDetail)

        movieCategory(onMovieClick = navController::toMovieDetail)

        movieDetailRoute(
            onBackClick = {
                navController.navigateUp()
            }, onRatingClick = navController::toRating
        )

        ratingRoute(onBackClick = { navController.navigateUp() })

        filterRoute(onApplyClick = {
            navController.apply {
                saveFilters(it)
                navigateUp()
            }

        }, onBackClick = { navController.navigateUp() })
    }
}