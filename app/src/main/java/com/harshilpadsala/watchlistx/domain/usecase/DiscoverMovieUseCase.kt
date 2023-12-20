package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {

    operator fun invoke(movieList: MovieList, page: Int): Flow<ResponseX<Content<Movie>?>> {
        return flow {
            emit(ResponseX.Loading)
            kotlin.runCatching {
                runCatching {
                    when (movieList) {
                        MovieList.NowPlaying -> discoverRepo.nowPlaying(page = page)
                        MovieList.Popular -> discoverRepo.popular(page = page)
                        MovieList.TopRated -> discoverRepo.topRated(page = page)
                        MovieList.Upcoming -> discoverRepo.upcoming(page = page)
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