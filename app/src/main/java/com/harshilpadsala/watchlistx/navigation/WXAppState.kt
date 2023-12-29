package com.harshilpadsala.watchlistx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harshilpadsala.watchlistx.constants.BottomNavItem
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberWXAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),

    ): WXAppState {
    return remember {
        WXAppState(
            navController, coroutineScope
        )
    }
}


class WXAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,


    ) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination


    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route == BottomNavItem.Discover.route || currentDestination?.route == BottomNavItem.Favourites.route || currentDestination?.route == BottomNavItem.Home.route
}