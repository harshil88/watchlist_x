package com.harshilpadsala.watchlistx.domain.usecase

import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.data.res.list.Content
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.repo.FavouriteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//todo - Add account Id From Session Manager

const val TEMP_ACCOUNT_ID = 12375371

class FavouriteMoviesUseCase @Inject constructor(
    private val favouriteRepo: FavouriteRepo
) {


    operator fun invoke(
        favouriteType: FavouriteType,
        page: Int = 1): Flow<ResultX<Content<MovieContent>?>> {
        return flow {
            kotlin.runCatching {
                runCatching {
                    if(favouriteType == FavouriteType.Favourite){
                        favouriteRepo.favouriteMovies(page = page , accountId = TEMP_ACCOUNT_ID)
                    }
                    else{
                        favouriteRepo.watchListMovies(page = page , accountId = TEMP_ACCOUNT_ID)
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
