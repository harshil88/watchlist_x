package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.constants.addX
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.FavouriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FavouriteUiState(
    var isFavouriteLoading: Boolean? = null,
    var isWishlistLoading: Boolean? = null,
    var hasReachedEndForFavourites: Boolean = false,
    var hasReachedEndForWatchlist: Boolean = false,
    var favouriteMovies: List<ListItemXData>? = null,
    var watchlistMovies: List<ListItemXData>? = null,
    var errorForFav: String? = null,
    var errorForWishlist: String? = null,
    var currentTabType: FavouriteType = FavouriteType.Favourite,
    var currentFavouritePage: Int = 1,
    var currentWatchListPage: Int = 1,
)

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favoriteMovieUseCase: FavouriteMoviesUseCase
) : ViewModel() {
    var favouriteUiState by mutableStateOf(FavouriteUiState())

    private var favCurrentPage = 1
    private var wishlistCurrentPage = 1

    init {
        favouriteMovies()
    }

    fun changeTab(favouriteType: FavouriteType) {
        favouriteUiState = favouriteUiState.copy(
            currentTabType = favouriteType
        )
        if (favouriteUiState.watchlistMovies == null) {
            watchListMovies()
        }
    }

    fun nextPage(favouriteType: FavouriteType) {
        if (favouriteType == FavouriteType.Favourite) {
            if (favouriteUiState.isFavouriteLoading == false) {
                favouriteUiState = favouriteUiState.copy(isFavouriteLoading = true)
                favouriteMovies()
            }
        } else {
            if (favouriteUiState.isWishlistLoading == false) {
                favouriteUiState = favouriteUiState.copy(isWishlistLoading = true)
                watchListMovies()
            }
        }
    }

    fun reset(favouriteType: FavouriteType) {
        if (favouriteType == FavouriteType.Favourite) {
            favouriteUiState = favouriteUiState.copy(
                isFavouriteLoading = null,
                currentFavouritePage = 1,
                favouriteMovies = null,
                hasReachedEndForFavourites = false,
                errorForFav = null,
            )
            favouriteMovies()
        } else {
            favouriteUiState = favouriteUiState.copy(
                isWishlistLoading = null,
                currentWatchListPage = 1,
                watchlistMovies = null,
                hasReachedEndForWatchlist = false,
                errorForWishlist = null,
            )
            watchListMovies()
        }
    }


    private fun favouriteMovies() {
        viewModelScope.launch {
            favoriteMovieUseCase.invoke(
                favouriteType = FavouriteType.Favourite, page = favCurrentPage
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        favouriteUiState = favouriteUiState.copy(
                            isFavouriteLoading = false,
                            favouriteMovies = favouriteUiState.favouriteMovies.addX(it.data?.toListItemX()),
                            currentFavouritePage = favCurrentPage,
                            hasReachedEndForFavourites = it.data?.totalPages == favCurrentPage,
                        )

                        if (!favouriteUiState.hasReachedEndForFavourites) {
                            favCurrentPage += 1
                        }
                    }

                    is ResultX.Error -> {
                        favouriteUiState = favouriteUiState.copy(
                            errorForFav = it.message,
                            isFavouriteLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun watchListMovies() {
        viewModelScope.launch {
            favoriteMovieUseCase.invoke(
                favouriteType = FavouriteType.Watchlist, page = wishlistCurrentPage
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        favouriteUiState = favouriteUiState.copy(
                            isWishlistLoading = false,
                            watchlistMovies =  favouriteUiState.watchlistMovies.addX(it.data?.toListItemX()),
                            currentWatchListPage = wishlistCurrentPage,
                            hasReachedEndForWatchlist = it.data?.totalPages == wishlistCurrentPage
                        )


                        if (!favouriteUiState.hasReachedEndForWatchlist) {
                            wishlistCurrentPage += 1
                        }
                    }

                    is ResultX.Error -> {
                        favouriteUiState = favouriteUiState.copy(
                            errorForWishlist = it.message,
                            isWishlistLoading = false,
                        )
                    }
                }
            }
        }
    }
}