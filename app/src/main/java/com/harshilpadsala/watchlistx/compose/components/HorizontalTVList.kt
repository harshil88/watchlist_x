package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.data.TVShowDetails
import utils.PaddingX

@Composable
fun HorizontalTVList(
    shows: List<TVShowDetails>,
    modifier: Modifier,
    onMovieClick: (TVShowDetails) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = shows.size,
        ) { index ->
            PaddingX(
                start = if(index == 0) 16.dp else 0.dp
            ) {
                MovieDetailTile(
                    title = shows[index].name,
                    posterPath = Constant.TMDB_IMAGE_URI + shows[index].posterPath,
                ) {
                    onMovieClick(shows[index])
                }
            }

        }
    }
}