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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.harshilpadsala.watchlistx.constants.BottomNavItem
import com.harshilpadsala.watchlistx.navigation.WatchListXNavigation
import com.harshilpadsala.watchlistx.navigation.WXAppState
import com.harshilpadsala.watchlistx.navigation.rememberWXAppState
import com.harshilpadsala.watchlistx.ui.theme.WatchlistXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchlistXTheme(darkTheme = true) {



                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    WXApp(
                        appState = rememberWXAppState()
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun WXApp(appState: WXAppState) {

    Scaffold(bottomBar = {
        MainBottomNav(appState)
    }) {
        WatchListXNavigation(navController = appState.navController)
    }
}

@Composable
fun MainBottomNav(appState: WXAppState) {

    val navStackBackEntry by appState.navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    val items = listOf(
        BottomNavItem.Home, BottomNavItem.Discover, BottomNavItem.Favourites
    )

    if (appState.shouldShowBottomBar) {
        BottomNavigation {
            items.forEach { item ->
                BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        appState.navController.navigate(item.route) {
                            popUpTo(appState.navController.graph.startDestinationId)
                        }
                    },
                    label = { Text(text = item.route) },
                    icon = {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
                    })
            }
        }
    }

}

