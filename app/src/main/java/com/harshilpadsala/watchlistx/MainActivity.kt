package com.harshilpadsala.watchlistx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.harshilpadsala.watchlistx.constants.WXNavItem
import com.harshilpadsala.watchlistx.constants.titleCase
import com.harshilpadsala.watchlistx.navigation.WXAppState
import com.harshilpadsala.watchlistx.navigation.WatchListXNavigation
import com.harshilpadsala.watchlistx.navigation.rememberWXAppState
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.ui.theme.WatchlistXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchlistXTheme(darkTheme = true) {
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun WXApp(appState: WXAppState) {
    Scaffold(bottomBar = {
        MainBottomNav(appState)
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            WatchListXNavigation(navController = appState.navController)
        }
    }
}


//Todo : Bottom Nav Popping to Home but if Home itself is there then??

@Composable
fun MainBottomNav(appState: WXAppState) {

    val navStackBackEntry by appState.navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    if (appState.shouldShowBottomBar) {
        BottomNavigation(
            backgroundColor = Darkness.night
        ) {
            WXNavItem.values().forEach {

                    item ->

                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == item.name } == true
                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        appState.navController.navigate(item.name) {
                            popUpTo(appState.navController.graph.startDestinationId)
                        }
                    },
                    label = {
                        Text(
                            text = item.name.titleCase(), style = StylesX.labelMedium.copy(
                                color = if (isSelected) Darkness.rise else Darkness.grey
                            ), modifier = Modifier.padding(top = 8.dp)
                        )
                    },
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = "Menu Icon",
                            tint = if (isSelected) Darkness.rise else Darkness.grey
                        )
                    },
                )
            }
        }
    }
}

