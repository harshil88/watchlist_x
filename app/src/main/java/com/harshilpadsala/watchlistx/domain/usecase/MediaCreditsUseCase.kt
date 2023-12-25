package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.data.res.list.MovieCredits
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaCreditsUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(mediaType: MediaType , mediaId : Int): Flow<ResponseX<MovieCredits>> {
        return flow {
            emit(ResponseX.Loading)
            kotlin.runCatching {
                runCatching {
                    when (mediaType) {
                        MediaType.Movie -> homeRepo.getMovieCredits(mediaId.toLong())
                        MediaType.Tv -> homeRepo.getTvCredits(mediaId.toLong())
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