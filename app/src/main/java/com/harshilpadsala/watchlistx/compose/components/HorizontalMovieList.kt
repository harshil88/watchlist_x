package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.Constant.TMDB_IMAGE_URI
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import utils.PaddingX

@Composable
fun HorizontalMovieList(
    movies: List<MovieContent>,
    modifier: Modifier,
    onMovieClick: (MovieContent) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = movies.size,
        ) { index ->
            PaddingX(
                start = if (index == 0) 16.dp else 0.dp
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
