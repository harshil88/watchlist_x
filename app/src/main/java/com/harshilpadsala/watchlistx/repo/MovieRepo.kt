package com.harshilpadsala.watchlistx.repo

import com.harshilpadsala.watchlistx.data.Content
import com.harshilpadsala.watchlistx.data.MovieDetails
import com.harshilpadsala.watchlistx.data.TVShowDetails
import retrofit2.Response
import retrofit2.http.GET

interface MovieRepo {
    @GET("3/discover/movie")
    suspend fun getAllMovies() : Response<Content<MovieDetails>>

    @GET("3/discover/tv")
    suspend fun getAllTv() : Response<Content<TVShowDetails>>

}