package com.harshilpadsala.watchlistx.domain.usecase

import android.util.Log
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchKeywordsUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo,
) {

    operator fun invoke(query: String): Flow<ResultX<Content<KeywordContent>>> = flow {
        runCatching {
            discoverRepo.searchKeywords(query = query)
        }.onSuccess {
            Log.i("SheetDebug usecase" , it.body().toString())
            emit(ResultX.Success(data = it.body()))
        }.onFailure {
            Log.i("SheetDebug usecase" , it.message.toString())

            emit(ResultX.Error(message = it.message))
        }
    }
}