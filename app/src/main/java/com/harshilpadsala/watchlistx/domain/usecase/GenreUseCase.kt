package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.GenreList
import com.harshilpadsala.watchlistx.data.res.list.TVContent
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {
    operator fun invoke(): Flow<ResultX<GenreList?>> {
        return flow {
                runCatching {
                    homeRepo.genres()
                }.onSuccess {
                    emit(ResultX.Success(data = it.body()))
                }.onFailure {
                    emit(ResultX.Error(message = it.message))
                }
        }
    }
}