package com.harshilpadsala.watchlistx.compose.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.Constant.TMDB_IMAGE_URI
import com.harshilpadsala.watchlistx.data.MovieDetails
import utils.PaddingX

@Composable
fun HorizontalMovieList(
    movies: List<MovieDetails>,
    modifier: Modifier,
    onMovieClick: (MovieDetails) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = movies.size,
        ) { index ->
            PaddingX(
                start = if(index == 0) 16.dp else 0.dp
            ) {
                MovieDetailTile(
                    title = movies[index].title,
                    posterPath = TMDB_IMAGE_URI + movies[index].posterPath,
                ) {
                    onMovieClick(movies[index])
                }
            }

        }
    }
}
