package com.harshilpadsala.watchlistx.repo

import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.data.res.list.TVContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverRepo {



    @GET("3/discover/movie")
    suspend fun discoverMovie(
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("primary_release_date.gte") dateGte : String? = null,
        @Query("primary_release_date.lte") dateLte : String? = null,
        @Query("sort_by") sortBy : String? = null,
        @Query("with_genres") withGenres : String? = null,
        @Query("with_keywords") withKeywords : String? = null,

    ): Response<Content<MovieContent>>

    @GET("3/movie/now_playing")
    suspend fun nowPlaying(
        @Query("page") page: Int = 1,
    ): Response<Content<MovieContent>>

    @GET("3/movie/popular")
    suspend fun popular(
        @Query("page") page: Int = 1,
        ): Response<Content<MovieContent>>

    @GET("3/movie/top_rated")
    suspend fun topRated(
        @Query("page") page: Int = 1,
        ): Response<Content<MovieContent>>

    @GET("3/movie/upcoming")
    suspend fun upcoming(
        @Query("page") page: Int = 1,
        ): Response<Content<MovieContent>>


    @GET("3/search/keyword")
    suspend fun searchKeywords(
        @Query("page") page: Int = 1,
        @Query("query") query: String,
    ): Response<Content<KeywordContent>>

}