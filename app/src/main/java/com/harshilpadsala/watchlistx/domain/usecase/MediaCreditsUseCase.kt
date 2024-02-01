package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.MovieCredits
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaCreditsUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(movieId: Int): Flow<ResultX<MovieCredits>> {
        return flow {
            runCatching {
                homeRepo.getMovieCredits(movieId.toLong())
            }.onSuccess {
                emit(ResultX.Success(data = it.body()))
            }.onFailure {
                emit(ResultX.Error(message = it.message))
            }

        }
    }

}