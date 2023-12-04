package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.repo.MovieRepo
import com.harshilpadsala.watchlistx.state.FailureState
import com.harshilpadsala.watchlistx.state.InitialState
import com.harshilpadsala.watchlistx.state.MovieListState
import com.harshilpadsala.watchlistx.state.MovieListSuccess
import com.harshilpadsala.watchlistx.state.TVListSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor() : ViewModel() {

    @Inject
    lateinit var movieRepo: MovieRepo

    private val _movieState = mutableStateOf<MovieListState>(InitialState)
    val mutableState: State<MovieListState>
        get() = _movieState

    private val _tvShowState = mutableStateOf<MovieListState>(InitialState)
    val tvShowState: State<MovieListState>
        get() = _tvShowState


    fun fetchMovies() {
        viewModelScope.launch {
            kotlin.runCatching {
                movieRepo.getAllMovies()
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
                movieRepo.getAllTv()
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

}