package com.harshilpadsala.watchlistx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.DiscoverRoute
import com.harshilpadsala.watchlistx.compose.FavouriteScreen
import com.harshilpadsala.watchlistx.compose.HomeScreen
import com.harshilpadsala.watchlistx.compose.MovieDetailRoute
import com.harshilpadsala.watchlistx.compose.RatingRoute
import com.harshilpadsala.watchlistx.constants.WXNavItem

private const val movieDetailRoute = "movieDetailRoute"
private const val ratingRoute = "movieDetail"

const val movieIdNavArg = "movieId"
const val movieNameNavArg = "movieName"
const val isRatedNavArg = "isRated"
const val ratingsNavArg = "ratings"






@Composable
fun WatchListXNavigation(navController : NavHostController){
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
        composable(ratingRoute){}


        composable("$ratingRoute/{$movieIdNavArg}/{$isRatedNavArg}?$ratingsNavArg={$ratingsNavArg}" ,
            arguments = listOf(

            navArgument(
                name = movieIdNavArg
            ){
                type = NavType.StringType
                defaultValue = ""
            },

            navArgument(
                name = isRatedNavArg
            ){
                type = NavType.BoolType
                defaultValue = false
            },

            navArgument(
                name = ratingsNavArg
            ){
                type = NavType.FloatType
                defaultValue = 0.0
            },
        )
        ){
            RatingRoute(
                isRated = false,
                ratings = null,
                onBackPress = { /*TODO*/ },
            )
        }

        composable(
            "$movieDetailRoute/{$movieIdNavArg}", arguments = listOf(navArgument(
                name = movieIdNavArg,
            ) {
                type = NavType.LongType
                defaultValue = 5L
            })

        ) {


            //Todo :- Add Real Route parameters

            MovieDetailRoute(
                onBackClick = {},
                onRatingClick = {
                    navController.navigate("$ratingRoute/MovieName/false?$ratingsNavArg=5.5")
                },
            )
        }
        //  movieDetailGraph(navController)
    }
}