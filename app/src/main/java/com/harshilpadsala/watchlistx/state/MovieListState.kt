package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.MovieList

sealed class MovieListState

object InitialState : MovieListState()

data class SuccessState(val response : MovieList?) : MovieListState()

data class FailureState(val error : String?) : MovieListState()
