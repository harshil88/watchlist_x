package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.req.ToggleFavouriteRequest
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.Movie
import com.harshilpadsala.watchlistx.repo.DiscoverRepo
import com.harshilpadsala.watchlistx.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class WishListType{
    Favourites,
    Watchlist
}

class AddToWishListUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {

    //Todo : Add Session Manager for account Id

    val accountId = 12375371L

    operator fun invoke(mediaType: MediaType , mediaId : Int , wishListType: WishListType , wishList : Boolean): Flow<ResponseX<String>> {
        return flow {

            val request = ToggleFavouriteRequest(
                mediaType = mediaType.name,
                mediaId = mediaId,
                watchlist = if(wishListType == WishListType.Watchlist) wishList else null,
                favorite = if(wishListType == WishListType.Favourites) wishList else null,
            )

            emit(ResponseX.Loading)
            kotlin.runCatching {
                runCatching {
                    when (wishListType) {
                        WishListType.Watchlist -> homeRepo.toggleWatchlist(accountId =  accountId , request = request)
                        WishListType.Favourites -> homeRepo.toggleFavourite(accountId = accountId , request = request)
                    }
                }.onSuccess {
                    emit(ResponseX.Success(data = it.message()))
                }.onFailure {
                    emit(ResponseX.Error(message = it.message))
                }
            }
        }
    }

}