package com.harshilpadsala.watchlistx.constants

import androidx.annotation.DrawableRes
import com.harshilpadsala.watchlistx.R

sealed class BottomNavItem(
    val route: String,
    @DrawableRes val resId: Int,

    ) {
    object Home : BottomNavItem(
        route = "Home",
        resId = R.drawable.ic_launcher_background
    )

    object Discover : BottomNavItem(
        route = "Discover",
        resId = R.drawable.ic_launcher_background
    )

    object Favourites : BottomNavItem(
        route = "Favourites",
        resId = R.drawable.ic_launcher_background
    )
}


