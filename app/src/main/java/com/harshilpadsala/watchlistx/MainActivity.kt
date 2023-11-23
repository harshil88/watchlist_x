package com.harshilpadsala.watchlistx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harshilpadsala.watchlistx.compose.DiscoverScreen
import com.harshilpadsala.watchlistx.compose.FavouriteScreen
import com.harshilpadsala.watchlistx.compose.HomeScreen
import com.harshilpadsala.watchlistx.constants.BottomNavItem
import com.harshilpadsala.watchlistx.ui.theme.WatchlistXTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchlistXTheme {

                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomBarDisplay(navController = navController)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun BottomBarDisplay(navController : NavHostController){
    Scaffold(
        bottomBar = {
            MainBottomNav(navController  = navController)
        }
    ) {
          paddingValues -> NavHost(navController = navController, startDestination = BottomNavItem.Home.route,){
        composable(BottomNavItem.Home.route){ HomeScreen()}
        composable(BottomNavItem.Discover.route){ DiscoverScreen() }
        composable(BottomNavItem.Favourites.route){ FavouriteScreen() }
    }
    }
}

@Composable
fun MainBottomNav(navController : NavHostController){



    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Discover,
        BottomNavItem.Favourites
    )

    BottomNavigation {
        items.forEach {
                item -> BottomNavigationItem(
            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
            onClick = {
                      navController.navigate(item.route){
                          popUpTo(navController.graph.startDestinationId)
                      }
            },
            label = {Text(text = item.route)},
            icon = {
                Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
            }) }
    }
}

