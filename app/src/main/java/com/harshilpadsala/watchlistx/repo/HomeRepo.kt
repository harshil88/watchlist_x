package com.harshilpadsala.watchlistx.repo

import com.harshilpadsala.watchlistx.data.UpdateResponse
import com.harshilpadsala.watchlistx.data.req.ToggleFavouriteRequest
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.data.res.list.MovieCredits
import com.harshilpadsala.watchlistx.data.res.list.MovieImages
import com.harshilpadsala.watchlistx.data.res.list.TVShow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeRepo {
    @GET("3/discover/movie")
    suspend fun getAllMovies() : Response<Content<Movie>>

    @GET("3/discover/tv")
    suspend fun getAllTv() : Response<Content<TVShow>>

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId : Long) : Response<MovieDetails>

    @GET("3/movie/{movieId}/account_states")
    suspend fun getMovieStats(@Path("movieId") movieId : Long) : Response<MovieStats>

    @GET("3/tv/{tvId}/account_states")
    suspend fun getTvStats(@Path("tvId") tvId : Long) : Response<MovieStats>

    @GET("3/movie/{movieId}/images")
    suspend fun getMovieImages(@Path("movieId") movieId : Long) : Response<MovieImages>

    @GET("3/movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId : Long) : Response<MovieCredits>

    @POST("3/account/{accountId}/favourite")
    suspend fun toggleFavourite(@Path("accountId") accountId : Long , @Body request : ToggleFavouriteRequest) : Response<UpdateResponse>

    @POST("3/account/{accountId}/watchlist")
    suspend fun toggleWatchlist(@Path("accountId") accountId : Long , @Body request : ToggleFavouriteRequest) : Response<UpdateResponse>


}