package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.ImagePager
import com.harshilpadsala.watchlistx.state.FailureState
import com.harshilpadsala.watchlistx.state.InitialState
import com.harshilpadsala.watchlistx.state.MovieDetailSuccess
import com.harshilpadsala.watchlistx.state.MovieImagesSuccess
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

        RenderMovieDetail(movieId = movieId)
        RenderMovieImagesDetail()

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
            Text(text = movieDetailsState.response?.title ?: "")
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
            ImagePager(images = movieImagesState.response?.backdrops ?: listOf())
        }

        is FailureState -> {
            ToastX(message = movieImagesState.error ?: "")
        }


        else -> {
        }

    }
}

