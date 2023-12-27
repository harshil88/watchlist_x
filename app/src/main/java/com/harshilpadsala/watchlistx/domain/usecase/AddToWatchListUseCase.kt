package com.harshilpadsala.watchlistx.domain.usecase

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.req.ToggleFavouriteRequest
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
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

    operator fun invoke(mediaType: MediaType, mediaId : Int, watchListOperation: WatchListOperation, wishList : Boolean): Flow<ResponseX<String>> {
        return flow {

            val request = ToggleFavouriteRequest(
                mediaType = mediaType.name.lowercase(Locale.ROOT),
                mediaId = mediaId,
                watchlist = if(watchListOperation == WatchListOperation.Watchlist) wishList else null,
                favorite = if(watchListOperation == WatchListOperation.Favourites) wishList else null,
            )

            emit(ResponseX.Loading)
            kotlin.runCatching {
                runCatching {
                    when (watchListOperation) {
                        WatchListOperation.Watchlist -> homeRepo.toggleWatchlist(accountId =  accountId , request = request)
                        WatchListOperation.Favourites -> homeRepo.toggleFavourite(accountId = accountId , request = request)
                    }
                }.onSuccess {
                    Log.i("FavRes" , it.body().toString())
                    emit(ResponseX.Success(data = it.message()))
                }.onFailure {
                    Log.i("FavRes" , it.toString())
                    Log.i("FavRes" , it.message.toString())

                    emit(ResponseX.Error(message = it.message))
                }
            }
        }
    }

}