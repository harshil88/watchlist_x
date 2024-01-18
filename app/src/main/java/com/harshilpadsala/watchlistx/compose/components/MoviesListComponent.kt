package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.compose.components.base_x.ListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData

@Composable
fun MoviesList(
    hasReachedEnd: Boolean,
    movies: List<ListItemXData>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    onItemClick: (Int) -> Unit
) {

    LazyColumn(
        modifier = modifier, state = lazyListState
    ) {
        items(
            count = if (!hasReachedEnd) movies.size + 1 else movies.size
        ) { index ->

            if (!hasReachedEnd && index == movies.size) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Box(modifier = Modifier.padding(bottom = 8.dp)) {
                    ListItemX(
                        mediaId = movies[index].id,
                        title = movies[index].title,
                        voteAverage = movies[index].voteAverage,
                        thumbnailPath = Constant.TMDB_IMAGE_URI_HIGH + movies[index].posterPath,
                        originalLanguage = movies[index].originalLanguage,
                        releaseDate = movies[index].releaseDate,
                        modifier = Modifier.height(80.dp),
                        onItemClick = onItemClick,
                    )
                }
            }


        }
    }
}