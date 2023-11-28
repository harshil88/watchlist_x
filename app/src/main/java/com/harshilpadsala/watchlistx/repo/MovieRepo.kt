package com.harshilpadsala.watchlistx.repo

import com.harshilpadsala.watchlistx.data.MovieList
import retrofit2.Response
import retrofit2.http.GET

interface MovieRepo {
    @GET("3/discover/movie")
    suspend fun getAllMovies() : Response<MovieList>

}