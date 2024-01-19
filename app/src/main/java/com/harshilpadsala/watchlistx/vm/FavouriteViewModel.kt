package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.list.MovieContent
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.FavouriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FavouriteUiState(
    var isFavouriteLoading : Boolean? = null,
    var isWishlistLoading : Boolean? = null,
    var hasReachedEndForFavourites : Boolean = false,
    var hasReachedEndForWatchlist : Boolean = false,
    var favouriteMovies : List<ListItemXData>? = null ,
    var watchlistMovies : List<ListItemXData>? = null ,
    var error : String? = null,
    var currentTabType : FavouriteType = FavouriteType.Favourite,
    var currentFavouritePage : Int = 1,
    var currentWatchListPage : Int = 1,

)

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favoriteMovieUseCase : FavouriteMoviesUseCase
) : ViewModel() {
    var favouriteUiState by mutableStateOf(FavouriteUiState())

    var favCurrentPage = 1
    var wishlistCurrentPage = 1

    init {
        favouriteMovies()
    }

    fun changeTab(favouriteType: FavouriteType){
        favouriteUiState = favouriteUiState.copy(
            currentTabType = favouriteType
        )
        if(favouriteUiState.watchlistMovies==null){
            watchListMovies()
        }
    }

    fun nextPage(favouriteType: FavouriteType) {
        if(favouriteType == FavouriteType.Favourite){
            if (favouriteUiState.isFavouriteLoading == false) {
                favouriteUiState = favouriteUiState.copy(isFavouriteLoading = true)
                favouriteMovies()
            }
        }

        else{
            if (favouriteUiState.isWishlistLoading == false) {
                favouriteUiState = favouriteUiState.copy(isWishlistLoading = true)
                watchListMovies()
            }
        }
    }


    private fun favouriteMovies(){
        viewModelScope.launch {
            favoriteMovieUseCase.invoke(favouriteType = FavouriteType.Favourite , page = favCurrentPage)
                .collect{
                    when(it){
                        is ResultX.Success -> {

                            val alreadyPresentMovies = favouriteUiState.favouriteMovies?.toMutableList()
                            val newMovies =
                                it.data?.results?.map { item -> item.toListItemX() }?.toMutableList()
                                    ?: mutableListOf()

                            alreadyPresentMovies?.addAll(newMovies)

                            favouriteUiState = favouriteUiState.copy(
                                isFavouriteLoading = false,
                                favouriteMovies =  alreadyPresentMovies ?: newMovies,
                                currentFavouritePage = favCurrentPage,
                                hasReachedEndForFavourites =  it.data?.totalPages == favCurrentPage
                            )


                            if (!favouriteUiState.hasReachedEndForFavourites) {
                                favCurrentPage += 1
                            }
                        }
                        is ResultX.Error -> {
                            favouriteUiState.error = it.message
                        }
                    }
                }
        }
    }

    private fun watchListMovies(){
        viewModelScope.launch {
            favoriteMovieUseCase.invoke(favouriteType = FavouriteType.Watchlist , page = wishlistCurrentPage)
                .collect{
                    when(it){
                        is ResultX.Success -> {
                            val alreadyPresentMovies = favouriteUiState.watchlistMovies?.toMutableList()
                            val newMovies =
                                it.data?.results?.map { item -> item.toListItemX() }?.toMutableList()
                                    ?: mutableListOf()

                            alreadyPresentMovies?.addAll(newMovies)

                            favouriteUiState = favouriteUiState.copy(
                                isWishlistLoading = false,
                                watchlistMovies =  alreadyPresentMovies ?: newMovies,
                                currentWatchListPage = wishlistCurrentPage,
                                hasReachedEndForWatchlist =  it.data?.totalPages == wishlistCurrentPage
                            )


                            if (!favouriteUiState.hasReachedEndForWatchlist) {
                                wishlistCurrentPage += 1
                            }
                        }
                        is ResultX.Error -> {
                            favouriteUiState.error = it.message
                        }
                    }
                }
        }
    }


}