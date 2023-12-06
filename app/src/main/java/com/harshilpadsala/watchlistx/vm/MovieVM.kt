package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.repo.HomeRepo
import com.harshilpadsala.watchlistx.state.FailureState
import com.harshilpadsala.watchlistx.state.InitialState
import com.harshilpadsala.watchlistx.state.MovieCreditsSuccess
import com.harshilpadsala.watchlistx.state.MovieDetailSuccess
import com.harshilpadsala.watchlistx.state.MovieImagesSuccess
import com.harshilpadsala.watchlistx.state.MovieListState
import com.harshilpadsala.watchlistx.state.MovieListSuccess
import com.harshilpadsala.watchlistx.state.TVListSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor() : ViewModel() {

    @Inject
    lateinit var homeRepo: HomeRepo

    private val _movieState = mutableStateOf<MovieListState>(InitialState)
    val mutableState: State<MovieListState>
        get() = _movieState

    private val _tvShowState = mutableStateOf<MovieListState>(InitialState)
    val tvShowState: State<MovieListState>
        get() = _tvShowState

    private val _movieDetailsState = mutableStateOf<MovieListState>(InitialState)
    val movieDetailsState: State<MovieListState>
        get() = _movieDetailsState

    private val _movieImagesState = mutableStateOf<MovieListState>(InitialState)
    val movieImagesState: State<MovieListState>
        get() = _movieImagesState

    private val _movieCreditsState = mutableStateOf<MovieListState>(InitialState)
    val movieCreditsState: State<MovieListState>
        get() = _movieCreditsState




    fun fetchMovies() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepo.getAllMovies()
            }.onSuccess {
                _movieState.value = MovieListSuccess(
                    response = it.body()
                )


            }.onFailure {
                _movieState.value = FailureState(
                    error = it.message
                )
            }
        }
    }

    fun fetchTV() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepo.getAllTv()
            }.onSuccess {
                _tvShowState.value = TVListSuccess(
                    response = it.body()
                )
            }.onFailure {
                _tvShowState.value = FailureState(
                    error = it.message
                )
            }
        }
    }

    fun getMovieDetail(movieId : Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepo.getMovieDetails(movieId)
            }.onSuccess {
                _movieDetailsState.value = MovieDetailSuccess(
                    response = it.body()
                )
            }.onFailure {
                _movieDetailsState.value = FailureState(
                    error = it.message
                )
            }
        }
    }

    fun getMovieImages(movieId : Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepo.getMovieImages(movieId)
            }.onSuccess {
                _movieImagesState.value = MovieImagesSuccess(
                    response = it.body()
                )
            }.onFailure {
                _movieImagesState.value = FailureState(
                    error = it.message
                )
            }
        }
    }

    fun getMovieCredits(movieId : Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepo.getMovieCredits(movieId)
            }.onSuccess {
                _movieCreditsState.value = MovieCreditsSuccess(
                    response = it.body()
                )
            }.onFailure {
                _movieCreditsState.value = FailureState(
                    error = it.message
                )
            }
        }
    }

}