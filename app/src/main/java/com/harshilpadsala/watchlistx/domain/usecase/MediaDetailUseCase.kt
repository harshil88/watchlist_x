package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.detail.TVShowDetails
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaDetailUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

      fun invokeMovieDetails(movieId: Int): Flow<ResponseX<MovieDetails>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    homeRepo.getMovieDetails(movieId.toLong())
                }.onSuccess {
                    emit(ResponseX.Success(data = it.body()))
                }.onFailure {
                    emit(ResponseX.Error(message = it.message))
                }
            }
        }
    }

    fun invokeTvDetails(tvShowId: Int): Flow<ResponseX<TVShowDetails>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    homeRepo.getTvShowDetails(tvShowId.toLong())
                }.onSuccess {
                    emit(ResponseX.Success(data = it.body()))
                }.onFailure {
                    emit(ResponseX.Error(message = it.message))
                }
            }
        }
    }

}