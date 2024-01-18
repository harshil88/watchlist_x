package com.harshilpadsala.watchlistx.di

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor


//Todo : -Better Error Handling In Retrofit

object WatchListXInterceptors : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDJmNTlmZDk3MDdlY2IxODljNGYzMmM0NGY4MTk5YSIsInN1YiI6IjYyNzhkNzdlNzI0ZGUxMzM1MTJlOWVlNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2yOgGMZUzVMCRSGH4BKXx5zAHNPO6cB1FM3Mynw3x7s"
            ).build()


        return chain.proceed(request)
    }

}

val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

