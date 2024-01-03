package com.harshilpadsala.watchlistx.state

import com.harshilpadsala.watchlistx.data.UpdateResponse
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.data.res.list.MovieCredits
import com.harshilpadsala.watchlistx.data.res.list.MovieImages
import com.harshilpadsala.watchlistx.data.res.list.TVContent

sealed class MovieListState

object InitialState : MovieListState()

object LoadingState : MovieListState()

data class MovieListSuccess(val response : Content<MovieContent>?) : MovieListState()
data class TVListSuccess(val response : Content<TVContent>?) : MovieListState()

data class MovieDetailSuccess(val response : MovieDetails?) : MovieListState()

data class MovieImagesSuccess(val response : MovieImages?) : MovieListState()

data class MovieCreditsSuccess(val response : MovieCredits?) : MovieListState()

data class ToggleFavouriteSuccess(val response : UpdateResponse?) : MovieListState()


data class FailureState(val error : String?) : MovieListState()


