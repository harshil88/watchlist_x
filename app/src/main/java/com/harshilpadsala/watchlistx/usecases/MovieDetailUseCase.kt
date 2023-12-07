package com.harshilpadsala.watchlistx.usecases

import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.repo.HomeRepo
import com.harshilpadsala.watchlistx.state.MovieListState
import retrofit2.Response
import javax.inject.Inject


sealed interface ResponseX
data class SuccessX<T>(val data : T?) : ResponseX
data class ErrorX(val message : String?) : ResponseX

interface MovieDetailUseCase {
    suspend operator fun invoke() : ResponseX
}

//class MovieDetailUseCaseImpl(val movieId : Long) : MovieDetailUseCase{
//
//    @Inject
//    lateinit var homeRepo : HomeRepo
//    override suspend fun invoke(): ResponseX {
//
//    }
//}