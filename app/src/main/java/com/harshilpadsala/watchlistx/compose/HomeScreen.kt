package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.HorizontalMovieList
import com.harshilpadsala.watchlistx.compose.components.HorizontalTVList
import com.harshilpadsala.watchlistx.state.FailureState
import com.harshilpadsala.watchlistx.state.InitialState
import com.harshilpadsala.watchlistx.state.MovieListSuccess
import com.harshilpadsala.watchlistx.state.TVListSuccess
import com.harshilpadsala.watchlistx.vm.MovieVM
import utils.LoaderX
import utils.PaddingX
import utils.ToastX

@Composable
fun HomeScreen(
    onClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        PaddingX(top = 24.dp, bottom = 24.dp) {
            RenderDiscoverMoviesList(
                onClick = onClick
            )
        }


        RenderDiscoverTVList(onClick = onClick)


    }
}

@Composable
fun RenderDiscoverMoviesList(movieViewModel: MovieVM = hiltViewModel() , onClick : (Long) -> Unit) {

    when (val movieState = movieViewModel.mutableState.value) {

        is InitialState -> {
            LoaderX()
            movieViewModel.fetchMovies()
        }

        is MovieListSuccess -> {
            movieViewModel.fetchTV()
            ListContent(title = "Discover Movies") {
                HorizontalMovieList(
                    movies = movieState.response?.results ?: listOf(), modifier = Modifier
                ) {
                    onClick(it.id?.toLong() ?: 0L)
                }
            }
        }

        is FailureState -> {
            ToastX(message = movieState.error ?: "")
            movieViewModel.fetchTV()
        }


        else -> {
        }

    }
}

@Composable
fun RenderDiscoverTVList(movieViewModel: MovieVM = hiltViewModel() , onClick : (Long) -> Unit) {

    when (val tvState = movieViewModel.tvShowState.value) {

        is TVListSuccess -> {
            ListContent(title = "Discover TV Shows") {
                HorizontalTVList(
                    shows = tvState.response?.results ?: listOf(), modifier = Modifier
                ) { tv ->
                    onClick(tv.id?.toLong() ?: 0L)
                }
            }
        }

        is FailureState -> {
            ToastX(message = tvState.error ?: "")
        }


        else -> {}

    }
}


@Composable
fun ListContent(
    title: String,
    moviesList: @Composable () -> Unit,
) {
    Column {
        PaddingX(start = 24.dp) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 24.sp, color = Color.Yellow, fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        moviesList()
    }
}
