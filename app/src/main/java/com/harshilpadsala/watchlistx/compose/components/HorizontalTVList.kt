package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.data.res.list.TVShow
import utils.PaddingX

@Composable
fun HorizontalTVList(
    shows: List<TVShow>,
    modifier: Modifier,
    onMovieClick: (TVShow) -> Unit,
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