package com.harshilpadsala.watchlistx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.harshilpadsala.watchlistx.compose.DiscoverRoute
import com.harshilpadsala.watchlistx.compose.FavouriteScreen
import com.harshilpadsala.watchlistx.compose.HomeScreen
import com.harshilpadsala.watchlistx.compose.MovieDetailRoute
import com.harshilpadsala.watchlistx.compose.RatingRoute
import com.harshilpadsala.watchlistx.constants.WXNavItem
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel

private const val movieDetailRoute = "movieDetailRoute"
private const val ratingRoute = "ratingRoute"

const val movieIdNavArg = "movieId"
const val movieNameNavArg = "movieName"
const val isRatedNavArg = "isRated"
const val ratingsNavArg = "ratings"
const val ratingArgs = "ratingArgs"


@Composable
fun WatchListXNavigation(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = WXNavItem.HOME.name
    ) {
        composable(WXNavItem.HOME.name) {
            HomeScreen { movieId ->
                navController.navigate("$movieDetailRoute/$movieId")
            }
        }

        composable(WXNavItem.DISCOVER.name) {
            DiscoverRoute(
                onMediaClick = {},
                onSearchClick = {},
            )
        }

        composable(WXNavItem.FAVOURITE.name) { FavouriteScreen() }


        composable(
            "$ratingRoute/{$ratingArgs}", arguments = listOf(

                navArgument(
                    name = ratingArgs
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            RatingRoute(
                onBackPress = {
                    navController.navigateUp()
                },
            )
        }

        composable("$movieDetailRoute/{$movieIdNavArg}", arguments = listOf(navArgument(
            name = movieIdNavArg,
        ) {
            type = NavType.LongType
            defaultValue = 5L
        })

        ) {


            //Todo :- Add Real Route parameters

            val ratingArgsModel = RatingArgsModel(
                movieId = 1100795,
                movieName = "Some Movie Name",
                posterPath = "gj74sUGsPMg5qDQooh8GTs4MvbP.jpg",
                isRated = false,
                ratings = 10,
            )

            val convertedToJson = Gson().toJson(ratingArgsModel)


            MovieDetailRoute(
                onBackClick = {},
                onRatingClick = {
                    navController.navigate("$ratingRoute/$convertedToJson")
                },
            )
        }
        //  movieDetailGraph(navController)
    }
}