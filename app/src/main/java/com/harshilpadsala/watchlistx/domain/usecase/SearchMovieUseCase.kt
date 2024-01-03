package com.harshilpadsala.watchlistx.domain.usecase

import android.util.Log
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {
     operator fun invoke(query: String): Flow<ResultX<Content<MovieContent>?>> = flow {
            kotlin.runCatching { discoverRepo.searchMovies(query = query)}
                .onSuccess {
                Log.i("SearchMovieUseCase" , "onSuccess")

                    emit(ResultX.Success(data = it.body()))
                }.onFailure {
                Log.i("SearchMovieUseCase" , "onFailure")
                    emit(ResultX.Error(message = it.message))
                }
        }
            .catch {
                Log.i("ErrorDebug", "Reaching")
            }
    }



