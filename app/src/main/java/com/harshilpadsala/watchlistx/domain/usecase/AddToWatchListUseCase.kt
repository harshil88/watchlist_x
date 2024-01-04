package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.UpdateResponse
import com.harshilpadsala.watchlistx.data.req.ToggleFavouriteRequest
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class WatchListOperation{
    Favourites,
    Watchlist,

}

class AddToWatchListUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    //Todo : Add Session Manager for account Id

    val accountId = 12375371L

    operator fun invoke(movieId : Int, watchListOperation: WatchListOperation, wishList : Boolean): Flow<ResultX<UpdateResponse>> {
        return flow {

            val request = ToggleFavouriteRequest(
                mediaType = "movie",
                mediaId = movieId,
                watchlist = if(watchListOperation == WatchListOperation.Watchlist) wishList else null,
                favorite = if(watchListOperation == WatchListOperation.Favourites) wishList else null,
            )

            kotlin.runCatching {
                runCatching {
                    when (watchListOperation) {
                        WatchListOperation.Watchlist -> homeRepo.toggleWatchlist(accountId =  accountId , request = request)
                        WatchListOperation.Favourites -> homeRepo.toggleFavourite(accountId = accountId , request = request)
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