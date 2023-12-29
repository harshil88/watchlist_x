package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.list.MovieImages
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaImagesUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(mediaType: MediaType, mediaId : Int): Flow<ResponseX<MovieImages>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    when (mediaType) {
                        MediaType.Movie -> homeRepo.getMovieImages(mediaId.toLong())
                        MediaType.Tv -> homeRepo.getTvImages(mediaId.toLong())
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