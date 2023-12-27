package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.CardListComponent
import com.harshilpadsala.watchlistx.compose.components.GenresRow
import com.harshilpadsala.watchlistx.compose.components.ImagePager
import com.harshilpadsala.watchlistx.compose.components.PrimaryButton
import com.harshilpadsala.watchlistx.compose.components.RatingRow
import com.harshilpadsala.watchlistx.data.res.detail.toCardComponent
import com.harshilpadsala.watchlistx.state.FailureState
import com.harshilpadsala.watchlistx.state.InitialState
import com.harshilpadsala.watchlistx.state.MovieCreditsSuccess
import com.harshilpadsala.watchlistx.state.MovieDetailSuccess
import com.harshilpadsala.watchlistx.state.MovieImagesSuccess
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.MovieVM
import utils.LoaderX
import utils.ToastX

@Preview
@Composable

fun MovieDetailScreenPreview() {
    MovieDetailScreen(movieId = 507089)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(movieId: Long, movieVM: MovieVM = hiltViewModel()) {
    Scaffold { paddingValues ->

        movieVM.getMovieDetail(movieId)
        movieVM.getMovieImages(movieId)
        movieVM.getMovieCredits(movieId)

        LazyColumn {
            item { RenderMovieDetail(movieId = movieId) }
            item { RenderMovieImagesDetail() }
            item { RenderMovieDetails() }
            item { RenderCastDetail() }
        }

    }
}

@Composable
fun RenderMovieDetail(movieId: Long, movieViewModel: MovieVM = hiltViewModel()) {

    when (val movieDetailsState = movieViewModel.movieDetailsState.value) {

        is InitialState -> {
            LoaderX()
            movieViewModel.fetchMovies()
        }

        is MovieDetailSuccess -> {
            movieViewModel.getMovieImages(movieId)
            Text(
                text = movieDetailsState.response?.title ?: "", modifier = Modifier.padding(
                    start = 24.dp, top = 24.dp, bottom = 24.dp
                ), style = StylesX.titleLarge.copy(color = Color.Yellow), maxLines = 1
            )
        }

        is FailureState -> {
            ToastX(message = movieDetailsState.error ?: "")
            movieViewModel.fetchTV()
        }


        else -> {
        }

    }
}

@Composable
fun RenderMovieImagesDetail(movieViewModel: MovieVM = hiltViewModel()) {

    when (val movieImagesState = movieViewModel.movieImagesState.value) {

        is MovieImagesSuccess -> {
           // ImagePager(images = movieImagesState.response?.backdrops ?: listOf())
        }

        is FailureState -> {
            ToastX(message = movieImagesState.error ?: "")
        }


        else -> {
        }

    }
}


@Composable
fun RenderMovieDetails(movieViewModel: MovieVM = hiltViewModel()) {

    when (val movieDetailState = movieViewModel.movieDetailsState.value) {

        is MovieDetailSuccess -> {


            GenresRow(
                genres = movieDetailState.response?.genres ?: listOf(),
                modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
            )
            Text(
                text = movieDetailState.response?.overview ?: "",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = StylesX.bodyMedium.copy(color = Darkness.grey),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            PrimaryButton(
                modifier = Modifier.padding(24.dp),
                onClick = { /*TODO*/ },
                pendingActionContent = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add to Watchlist Icon",
                        tint = Darkness.stillness
                    )
                    Text(
                        text = "Add to Watchlist",
                        modifier = Modifier.padding(
                            start = 24.dp
                        ),
                        style = StylesX.titleMedium.copy(color = Darkness.stillness),
                        maxLines = 1
                    )
                },
            ) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = "Added to Watchlist Icon",
                    tint = Darkness.light
                )
                Text(
                    text = "Added to Watchlist", modifier = Modifier.padding(
                        start = 24.dp
                    ), style = StylesX.titleMedium.copy(color = Darkness.light), maxLines = 1
                )
            }

            RatingRow(
                rating = movieDetailState.response?.voteAverage ?: 0.0,
                users = movieDetailState.response?.voteCount ?: 0
            )


        }

        else -> {
        }

    }
}

//@Composable
//fun RenderAddToWatchlistButton(movieViewModel: MovieVM = hiltViewModel()){
//    when(val toggleFavouriteState = movieViewModel.toggleFavouriteState.value){
//        is InitialState ->
//    }
//}

@Composable
fun RenderCastDetail(movieViewModel: MovieVM = hiltViewModel()) {

    when (val movieCreditsState = movieViewModel.movieCreditsState.value) {

        is MovieCreditsSuccess -> {
            Text(
                text = "Top Casts", modifier = Modifier.padding(
                    start = 24.dp, top = 24.dp, bottom = 24.dp
                ), style = StylesX.titleLarge.copy(color = Color.Yellow), maxLines = 1
            )
            movieCreditsState.response?.cast?.map { actorDetails -> actorDetails.toCardComponent() }
                ?.let {
                    CardListComponent(
                        cards = it.toMutableList(),
                        modifier = Modifier,
                        onCardClick = { id -> })
                }
        }

        is FailureState -> {
            ToastX(message = movieCreditsState.error ?: "")
        }


        else -> {
        }

    }
}
