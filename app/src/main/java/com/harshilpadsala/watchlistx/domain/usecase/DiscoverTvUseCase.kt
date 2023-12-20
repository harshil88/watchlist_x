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
                        TvList.AiringToday -> discoverRepo.airingToday(page = page)
                        TvList.OnTheAir -> discoverRepo.onTheAir(page = page)
                        TvList.Popular -> discoverRepo.popularTV(page = page)
                        TvList.TopRated -> discoverRepo.topRatedTV(page = page)
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