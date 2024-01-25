package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesByCategoryUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {

    operator fun invoke(movieCategory: MovieCategory, page: Int = 1): Flow<ResultX<Content<MovieContent>?>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    when (movieCategory) {
                        MovieCategory.NowPlaying -> discoverRepo.nowPlaying(page = page)
                        MovieCategory.Popular -> discoverRepo.popular(page = page)
                        MovieCategory.TopRated -> discoverRepo.topRated(page = page)
                        MovieCategory.Upcoming -> discoverRepo.upcoming(page = page)
                    }
                }.onSuccess {
                    emit(ResultX.Success(data = it.body()))
                }.onFailure {
                    emit(ResultX.Error(message = it.message))
                }
            }
        }
    }

}