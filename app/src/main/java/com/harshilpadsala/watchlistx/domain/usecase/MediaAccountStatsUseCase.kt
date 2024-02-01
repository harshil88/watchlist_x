package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.detail.parseRating
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaAccountStatsUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(movieId : Int): Flow<ResultX<MovieStats>> {
        return flow {
                runCatching {
                         homeRepo.getMovieStats(movieId.toLong())
                }.onSuccess {
                    emit(ResultX.Success(data = it.body()?.parseRating()))
                }.onFailure {

                    emit(ResultX.Error(message = it.message))
                }

        }
    }

}