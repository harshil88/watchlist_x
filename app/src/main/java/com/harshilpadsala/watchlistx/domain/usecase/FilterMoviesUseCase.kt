package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterMoviesUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {

    operator fun invoke(
        page: Int,
        dateGte: String?,
        dateLte: String?,
        sortBy: String?,
        withGenres: String?,
        withKeywords: String?
    ): Flow<ResultX<Content<MovieContent>>> = flow {
        runCatching {
            discoverRepo.discoverMovie(
                page = page,
                dateGte = dateGte,
                dateLte = dateLte,
                sortBy = sortBy,
                withGenres = withGenres,
                withKeywords = withKeywords
            )
        }.onSuccess {
            emit(ResultX.Success(data = it.body()))
        }.onFailure {
            emit(ResultX.Error(message = it.message))
        }
    }


}