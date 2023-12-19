package com.harshilpadsala.watchlistx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.harshilpadsala.watchlistx.compose.DiscoverRoute
import com.harshilpadsala.watchlistx.compose.DiscoverScreen
import com.harshilpadsala.watchlistx.compose.FavouriteScreen
import com.harshilpadsala.watchlistx.compose.HomeScreen
import com.harshilpadsala.watchlistx.compose.MovieDetailScreen
import com.harshilpadsala.watchlistx.constants.BottomNavItem
import com.harshilpadsala.watchlistx.ui.theme.WatchlistXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchlistXTheme(darkTheme = true) {


                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    BottomBarDisplay(navController = navController)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun BottomBarDisplay(navController: NavHostController) {
    val navStackBackEntry by navController.currentBackStackEntryAsState()

    Scaffold(bottomBar = {
        MainBottomNav(navController = navController)
    }) {
        NavHost(
            navController = navController, startDestination = BottomNavItem.Home.route
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen { movieId ->
                    navController.navigate("movieDetail/$movieId")
                }
            }
            composable(BottomNavItem.Discover.route) { DiscoverRoute(
                onMediaClick = {},
                onSearchClick = {},

            ) }
            composable(BottomNavItem.Favourites.route) { FavouriteScreen() }
            composable("movieDetail/{movieId}", arguments = listOf(navArgument(
                name = "movieId",
            ) {
                type = NavType.LongType
                defaultValue = 5L
            })

            ) {
                MovieDetailScreen(movieId = navStackBackEntry?.arguments?.getLong("movieId") ?: 0L)
            }
            //  movieDetailGraph(navController)
        }
    }
}

//fun NavGraphBuilder.movieDetailGraph(navController: NavController){
//
//
//
//    navigation(startDestination = "movieDetail" , route = "movieDetail/{movieId}"){
//        composable("movieDetail/{movieId}" , arguments = listOf(
//            navArgument("movieId"){
//                type = NavType.LongType
//                defaultValue = 5L
//            }
//        )){
//            backStackEntry ->
//            MovieDetailScreen(movieId = backStackEntry.arguments?.getLong("movieId")?:0)}
//    }
//}

@Composable
fun MainBottomNav(navController: NavHostController) {

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    val items = listOf(
        BottomNavItem.Home, BottomNavItem.Discover, BottomNavItem.Favourites
    )

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                    }
                },
                label = { Text(text = item.route) },
                icon = {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
                })
        }
    }
}

