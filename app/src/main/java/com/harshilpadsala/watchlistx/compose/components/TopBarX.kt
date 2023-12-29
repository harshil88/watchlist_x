package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarX(
    modifier: Modifier = Modifier,
    title: String,
    actions : @Composable RowScope.() -> Unit = {},
    onBackPress: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
        IconButton(onClick = onBackPress) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Navigate Backwards",
                tint = Darkness.light,
            )
        }
    }, title = {
        Text(text = title, style = StylesX.labelMedium.copy(color = Darkness.light))
    }, actions = actions
    )
}