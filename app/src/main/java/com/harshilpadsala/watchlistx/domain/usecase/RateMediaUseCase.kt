package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.UpdateResponse
import com.harshilpadsala.watchlistx.data.req.MediaRatingRequest
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class RatingOperation{
    AddRating,
    DeleteRating,
}

class RateMediaUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(mediaType: MediaType, mediaId : Int, ratingOperation: RatingOperation , value : Double): Flow<ResponseX<UpdateResponse>> {
        return flow {

            val request = MediaRatingRequest(
                value = value
            )

            emit(ResponseX.Loading)
            kotlin.runCatching {
                runCatching {
                   when(mediaType){
                        MediaType.Movie -> {
                            when(ratingOperation){
                                RatingOperation.AddRating -> homeRepo.rateMovie(movieId = mediaId.toLong(), request = request)
                                RatingOperation.DeleteRating -> homeRepo.deleteMovieRating(movieId = mediaId.toLong())
                            }
                        }

                       MediaType.Tv -> {
                           when(ratingOperation){
                               RatingOperation.AddRating -> homeRepo.rateTv(tvId = mediaId.toLong(), request = request)
                               RatingOperation.DeleteRating -> homeRepo.deleteTvRating(tvId = mediaId.toLong())
                           }
                       }
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