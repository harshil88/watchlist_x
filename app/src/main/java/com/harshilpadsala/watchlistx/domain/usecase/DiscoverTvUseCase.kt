package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.TVShow
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {
    operator fun invoke(tvList: TvList, page: Int): Flow<ResponseX<Content<TVShow>?>> {
        return flow {
            emit(ResponseX.Loading)
            kotlin.runCatching {
                runCatching {
                    when (tvList) {
                        TvList.AiringToday -> discoverRepo.airingToday()
                        TvList.OnTheAir -> discoverRepo.onTheAir()
                        TvList.Popular -> discoverRepo.popularTV()
                        TvList.TopRated -> discoverRepo.topRatedTV()
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