package com.harshilpadsala.watchlistx.constants

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.harshilpadsala.watchlistx.R




enum class WXNavItem(
    val icon : ImageVector
){
    HOME(icon = Icons.Filled.Home),
    DISCOVER(icon = Icons.Filled.Movie),
    FAVOURITE(icon = Icons.Filled.Favorite)

}




