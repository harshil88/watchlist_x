package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.res.list.MovieImages
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaImagesUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {
    operator fun invoke( mediaId : Int): Flow<ResultX<MovieImages>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                        homeRepo.getMovieImages(mediaId.toLong())
                }.onSuccess {
                    emit(ResultX.Success(data = it.body()))
                }.onFailure {
                    emit(ResultX.Error(message = it.message))
                }
            }
        }
    }

}