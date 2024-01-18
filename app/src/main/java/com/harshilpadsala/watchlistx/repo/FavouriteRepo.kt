package com.harshilpadsala.watchlistx.repo

import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FavouriteRepo {

    @GET("3/account/{account_id}/favorite/movies")
    suspend fun favouriteMovies(
        @Path("account_id") accountId : Int,
        @Query("page") page: Int = 1,
    ): Response<Content<MovieContent>>

    @GET("3/account/{account_id}/watchlist/movies")
    suspend fun watchListMovies(
        @Path("account_id") accountId : Int,
        @Query("page") page: Int = 1,
    ): Response<Content<MovieContent>>
}