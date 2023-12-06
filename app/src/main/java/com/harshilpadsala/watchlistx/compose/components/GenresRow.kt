package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.data.Genre
import com.harshilpadsala.watchlistx.ui.theme.Darkness

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun GenresRow(genres : List<Genre> , modifier: Modifier){
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        repeat(genres.size){
            index ->
            Chip(onClick = { /*TODO*/ } , enabled = false , colors = ChipDefaults.chipColors(
                disabledBackgroundColor = Darkness.midnight,
                disabledContentColor = Darkness.light,

            )) {
                Text(text = genres[index].name?:"")
            }
        }
    }
}