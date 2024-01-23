//package com.harshilpadsala.watchlistx.navigation.nav_graphs
//
//import android.net.Uri
//import androidx.navigation.NavType
//import com.google.gson.Gson
//import com.harshilpadsala.watchlistx.compose.DiscoverRoute
//import com.harshilpadsala.watchlistx.compose.FavouriteRoute
//import com.harshilpadsala.watchlistx.compose.FilterRoute
//import com.harshilpadsala.watchlistx.compose.MovieDetailRoute
//import com.harshilpadsala.watchlistx.compose.RatingRoute
//import com.harshilpadsala.watchlistx.data.res.model.FilterParams
//import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
//import com.harshilpadsala.watchlistx.navigation.filterNavArg
//import com.harshilpadsala.watchlistx.navigation.filterRoute
//import com.harshilpadsala.watchlistx.navigation.ratingRoute
//
//
//composable(
//WXNavItem.DISCOVER.name,
//)
//{
//    entry ->
//    val encodedUri = entry.savedStateHandle.get<String>(filterNavArg)
//    val decodedUri = encodedUri.let { Uri.decode(encodedUri) }
//    val filterArgs = if (decodedUri != null) {
//        Gson().fromJson(decodedUri, FilterParams::class.java)
//    } else FilterParams()
//
//
//    DiscoverRoute(
//        filterParams = filterArgs,
//        onMovieClick = {},
//        onFilterClick = {
//            navController.navigate(filterRoute)
//        },
//    )
//}
//
//composable(WXNavItem.FAVOURITE.name) {
//    FavouriteRoute(
//        onMovieClick = {}
//    )
//}
//
//
//composable(
//"$ratingRoute/{$ratingArgs}", arguments = listOf(navArgument(
//name = ratingArgs
//) {
//    type = NavType.StringType
//    defaultValue = ""
//})
//) {
//    RatingRoute(
//        onBackPress = {
//            navController.navigateUp()
//        },
//    )
//}
//
//composable(
//"$movieDetailRoute/{$movieIdNavArg}", arguments = listOf(
//navArgument(
//name = movieIdNavArg,
//) {
//    type = NavType.LongType
//    defaultValue = 5L
//})
//
//) {
//
//    //Todo :- Add Real Route parameters
//
//    val ratingArgsModel = RatingArgsModel(
//        movieId = 1100795,
//        movieName = "Some Movie Name",
//        posterPath = "gj74sUGsPMg5qDQooh8GTs4MvbP.jpg",
//        isRated = true,
//        ratings = 10,
//    )
//
//    val convertedToJson = Gson().toJson(ratingArgsModel)
//
//    MovieDetailRoute(
//        onBackClick = {},
//        onRatingClick = {
//            navController.navigate("$ratingRoute/$convertedToJson")
//        },
//    )
//}
//
//composable(
//filterRoute
//) {
//    FilterRoute(
//        onApplyClick = { args ->
//            val jsonParsed = Gson().toJson(args)
//            val encodedUri = Uri.encode(jsonParsed)
//            navController.previousBackStackEntry?.savedStateHandle?.set(
//                filterNavArg, encodedUri
//            )
//        }, onBackClick = {
//            navController.navigateUp()
//        })
//}
//
