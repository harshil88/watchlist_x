package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.compose.components.base_x.GenreChipX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent

//todo :- Add on Chip Click

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun GenresRow(
    genres: List<GenreContent>,
    modifier: Modifier = Modifier,
    onGenreClick: (Int) -> Unit = {}
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier
    ) {
        genres.map { genre ->
            GenreChipX(
                text = genre.name ?: "Unknown Genre",
                onGenreClick = { onGenreClick(genre.id ?: 0) })
        }
    }
}