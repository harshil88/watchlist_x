package com.harshilpadsala.watchlistx.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.FilterRoute
import com.harshilpadsala.watchlistx.compose.RatingRoute
import com.harshilpadsala.watchlistx.constants.toEncodedUri
import com.harshilpadsala.watchlistx.data.res.model.FilterParams
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.navigation.ArgumentsX

const val filterRoute = "FilterRoute"

fun NavController.toFilter( navOptions: NavOptions? = null) {
    this.navigate(filterRoute, navOptions)
}

fun NavGraphBuilder.filterRoute(
    onApplyClick : (FilterParams) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = filterRoute,
    ) {
        FilterRoute(
            onApplyClick = onApplyClick,
            onBackClick = onBackClick
        )
    }
}