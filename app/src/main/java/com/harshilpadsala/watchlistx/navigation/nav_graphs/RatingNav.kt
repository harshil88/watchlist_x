package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.RatingRoute
import com.harshilpadsala.watchlistx.constants.toEncodedUri
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.navigation.ArgumentsX

const val ratingRoute = "RatingRoute"

fun NavController.toRating(rating : RatingArgsModel, navOptions: NavOptions? = null) {
    this.navigate("$ratingRoute/${rating.toEncodedUri()}", navOptions)
}

fun NavGraphBuilder.ratingRoute(
    onBackClick: () -> Unit,
) {
    composable(
        route = "${ratingRoute}/{${ArgumentsX.ratingJson}}",
        arguments = listOf(
            navArgument(
                name = ArgumentsX.ratingJson,
            ) {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            } ,),
    ) {
        RatingRoute(
            onBackPress = onBackClick
        )
    }
}