package com.harshilpadsala.watchlistx.domain.usecase

import android.util.Log
import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val discoverRepo: DiscoverRepo
) {
     operator fun invoke(query: String): Flow<ResponseX<Content<Movie>?>> = flow {
            kotlin.runCatching { discoverRepo.searchMovies(query = query)}
                .onSuccess {
                Log.i("SearchMovieUseCase" , "onSuccess")

                    emit(ResponseX.Success(data = it.body()))
                }.onFailure {
                Log.i("SearchMovieUseCase" , "onFailure")
                    emit(ResponseX.Error(message = it.message))
                }
        }
            .catch {
                Log.i("ErrorDebug", "Reaching")
            }
    }



