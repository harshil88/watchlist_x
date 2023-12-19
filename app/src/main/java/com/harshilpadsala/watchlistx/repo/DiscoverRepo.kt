package com.harshilpadsala.watchlistx.repo

import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.data.res.list.TVShow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverRepo {

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("year") year: Int? = null,
    ): Response<Content<Movie>>

    @GET("3/movie/now_playing")
    suspend fun nowPlaying(
        @Query("page") page: Int = 1,
    ): Response<Content<Movie>>

    @GET("3/movie/popular")
    suspend fun popular(
        @Query("page") page: Int = 1,
        ): Response<Content<Movie>>

    @GET("3/movie/top_rated")
    suspend fun topRated(
        @Query("page") page: Int = 1,
        ): Response<Content<Movie>>

    @GET("3/movie/upcoming")
    suspend fun upcoming(
        @Query("page") page: Int = 1,
        ): Response<Content<Movie>>

    @GET("3/tv/airing_today")
    suspend fun airingToday(
        @Query("page") page: Int = 1,
        ): Response<Content<TVShow>>

    @GET("3/tv/on_the_air")
    suspend fun onTheAir(
        @Query("page") page: Int = 1,
        ): Response<Content<TVShow>>

    @GET("3/tv/popular")
    suspend fun popularTV(
        @Query("page") page: Int = 1,
        ): Response<Content<TVShow>>

    @GET("3/tv/top_rated")
    suspend fun topRatedTV(
        @Query("page") page: Int = 1,
        ): Response<Content<TVShow>>
}