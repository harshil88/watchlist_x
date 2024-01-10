package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.TVContent
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {
    operator fun invoke(tvList: TvList, page: Int): Flow<ResultX<Content<TVContent>?>> {
        return flow {

        }
    }
}