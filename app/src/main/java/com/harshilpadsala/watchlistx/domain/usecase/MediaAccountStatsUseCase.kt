package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.req.ToggleFavouriteRequest
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaAccountStatsUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(mediaType: MediaType , mediaId : Int): Flow<ResponseX<MovieStats>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    when (mediaType) {
                        MediaType.Movie -> homeRepo.getMovieStats(mediaId.toLong())
                        MediaType.Tv -> homeRepo.getTvStats(mediaId.toLong())
                    }
                }.onSuccess {
                    emit(ResponseX.Success(data = it.body()))
                }.onFailure {
                    emit(ResponseX.Error(message = it.message))
                }
            }
        }
    }

}