package com.harshilpadsala.watchlistx.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.harshilpadsala.watchlistx.compose.DiscoverRoute
import com.harshilpadsala.watchlistx.compose.FavouriteRoute
import com.harshilpadsala.watchlistx.compose.FilterRoute
import com.harshilpadsala.watchlistx.compose.HomeRoute
import com.harshilpadsala.watchlistx.compose.MovieDetailRoute
import com.harshilpadsala.watchlistx.compose.RatingRoute
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.constants.WXNavItem
import com.harshilpadsala.watchlistx.data.res.model.FilterParams
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel

private const val movieDetailRoute = "movieDetailRoute"
private const val ratingRoute = "ratingRoute"
private const val filterRoute = "filterRoute"


const val movieIdNavArg = "movieId"
const val filterNavArg = "filterNavArg"
const val ratingArgs = "ratingArgs"
const val movieListTypeArg = "movieListTypeArg"


@Composable
fun WatchListXNavigation(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = WXNavItem.HOME.name
    ) {
        composable(WXNavItem.HOME.name) {
            HomeRoute(
                onMediaClick = { mediaId -> navController.navigate("$movieDetailRoute/$mediaId") },
                onGenreClick = {},
                onRatingClick = { ratingArgsModel ->
                    val convertedToJson = Gson().toJson(
                        ratingArgsModel
                    )
                    val encodedUri = Uri.encode(convertedToJson)
                    navController.navigate("$ratingRoute/$encodedUri")
                },
                onShowMoreClick = {
                    movieList ->
                    navController.navigate("${WXNavItem.DISCOVER.name}?$movieListTypeArg={${MovieList.NowPlaying}}")
                },
            )
        }

        composable(
            WXNavItem.DISCOVER.name,
        )
        {
                entry ->
            val encodedUri = entry.savedStateHandle.get<String>(filterNavArg)
            val decodedUri = encodedUri.let { Uri.decode(encodedUri) }
            val filterArgs = if (decodedUri != null) {
                Gson().fromJson(decodedUri, FilterParams::class.java)
            } else FilterParams()


            DiscoverRoute(
                filterParams = filterArgs,
                onMovieClick = {},
                onFilterClick = {
                    navController.navigate(filterRoute)
                },
            )
        }

        composable(WXNavItem.FAVOURITE.name) {
            FavouriteRoute(
                onMovieClick = {}
            )
        }


        composable(
            "$ratingRoute/{$ratingArgs}", arguments = listOf(navArgument(
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

        composable(
            "$movieDetailRoute/{$movieIdNavArg}", arguments = listOf(
                navArgument(
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
                isRated = true,
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

        composable(
            filterRoute
        ) {
            FilterRoute(
                onApplyClick = { args ->
                val jsonParsed = Gson().toJson(args)
                val encodedUri = Uri.encode(jsonParsed)
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    filterNavArg, encodedUri
                )
            }, onBackClick = {
                navController.navigateUp()
            })
        }


    }
}