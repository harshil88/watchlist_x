package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX


@Composable
fun WXToggleIconButton(
    state: Boolean,
    modifier: Modifier = Modifier,
    favouriteType: FavouriteType,
    onClick: () -> Unit = {},
) {

    ElevatedButton(
        enabled = true,
        modifier = modifier
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state) Darkness.rise else Darkness.water,
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            2.dp,
            Darkness.rise
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = if (favouriteType == FavouriteType.Watchlist) Icons.Filled.Bookmark else Icons.Filled.Favorite,
            contentDescription = "Toggle Button Content",
            tint = if (state) Darkness.stillness else Darkness.rise
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text =
        if (state) "Remove From ${favouriteType.name}"
        else "Add To ${favouriteType.name}",
             style = StylesX.titleMedium.copy(
                 color =  if (state) Darkness.stillness else Darkness.rise
            ))

    }
}

