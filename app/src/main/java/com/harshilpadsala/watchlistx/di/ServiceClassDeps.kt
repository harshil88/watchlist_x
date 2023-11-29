package com.harshilpadsala.watchlistx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object ServiceClassDeps {

    @Provides
    fun providesBaseUri() : String =  "https://api.themoviedb.org"

    @Provides
    fun providesGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun providesOkHttpClient() : OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(WatchListXInterceptors)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun providesRetrofitObject(baseUri : String, gsonConverterFactory: GsonConverterFactory , okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(baseUri)
        .addConverterFactory(
            gsonConverterFactory
        )
        .client(okHttpClient)
        .build()

}