package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.data.res.list.MovieCredits
import com.harshilpadsala.watchlistx.data.res.list.MovieImages
import com.harshilpadsala.watchlistx.data.res.list.TVShow

sealed class MovieListState

object InitialState : MovieListState()
data class MovieListSuccess(val response : Content<Movie>?) : MovieListState()
data class TVListSuccess(val response : Content<TVShow>?) : MovieListState()

data class MovieDetailSuccess(val response : MovieDetails?) : MovieListState()

data class MovieImagesSuccess(val response : MovieImages?) : MovieListState()

data class MovieCreditsSuccess(val response : MovieCredits?) : MovieListState()



data class FailureState(val error : String?) : MovieListState()


