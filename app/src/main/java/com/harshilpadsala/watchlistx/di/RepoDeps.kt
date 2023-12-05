package com.harshilpadsala.watchlistx.di

import com.harshilpadsala.watchlistx.repo.HomeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoDeps {

    @Provides
    @Singleton
    fun providesMovieRepo(retrofit: Retrofit) : HomeRepo = retrofit.create(HomeRepo::class.java)

}