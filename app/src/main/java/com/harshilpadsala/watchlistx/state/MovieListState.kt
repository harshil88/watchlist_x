package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.Content
import com.harshilpadsala.watchlistx.data.MovieDetails
import com.harshilpadsala.watchlistx.data.TVShowDetails

sealed class MovieListState

object InitialState : MovieListState()
data class MovieListSuccess(val response : Content<MovieDetails>?) : MovieListState()
data class TVListSuccess(val response : Content<TVShowDetails>?) : MovieListState()
data class FailureState(val error : String?) : MovieListState()


