package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.harshilpadsala.watchlistx.ui.theme.Darkness

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenreChipX(text: String, onGenreClick: () -> Unit) {
    Chip(
        onClick = onGenreClick, enabled = false, colors = ChipDefaults.chipColors(
            disabledBackgroundColor = Darkness.midnight,
            disabledContentColor = Darkness.light,

            )
    ) {
        Text(text = text)
    }
}