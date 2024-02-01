package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaDetailUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    fun invoke(movieId: Int): Flow<ResultX<MovieDetails>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    homeRepo.getMovieDetails(movieId.toLong())
                }.onSuccess {
                    emit(ResultX.Success(data = it.body()))
                }.onFailure {
                    emit(ResultX.Error(message = it.message))
                }
            }
        }
    }


}