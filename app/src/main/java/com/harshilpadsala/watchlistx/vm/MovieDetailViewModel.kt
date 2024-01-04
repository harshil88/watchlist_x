package com.harshilpadsala.watchlistx.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.detail.toPresentation
import com.harshilpadsala.watchlistx.data.res.list.MovieImages
import com.harshilpadsala.watchlistx.data.res.list.toImageList
import com.harshilpadsala.watchlistx.domain.usecase.AddToWatchListUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaAccountStatsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaCreditsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaDetailUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaImagesUseCase
import com.harshilpadsala.watchlistx.domain.usecase.RateMediaUseCase
import com.harshilpadsala.watchlistx.domain.usecase.WatchListOperation
import com.harshilpadsala.watchlistx.state.movie_detail.CreditsUiState
import com.harshilpadsala.watchlistx.state.movie_detail.FavouriteUiState
import com.harshilpadsala.watchlistx.state.movie_detail.MovieDetailUiState
import com.harshilpadsala.watchlistx.state.movie_detail.WatchlistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    mediaStatsUseCase: MediaAccountStatsUseCase,
    mediaDetailUseCase: MediaDetailUseCase,
    mediaImagesUseCase: MediaImagesUseCase,
    private val watchListUseCase: AddToWatchListUseCase,
    private val rateMediaUseCase: RateMediaUseCase,
    private val movieCreditsUseCase: MediaCreditsUseCase,
) : ViewModel() {

    private val tempMovieId = 901362

    val favouriteUiState = MutableStateFlow<FavouriteUiState>(FavouriteUiState.Loading)

    val watchListUiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)

    val ratingUiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)

    val mediaCreditsStateFlow = MutableStateFlow<CreditsUiState>(CreditsUiState.Loading)

    private val mediaStatsFlow = mediaStatsUseCase.invoke(
        mediaType = MediaType.Movie,
        mediaId = tempMovieId,
    )

    private val movieDetailFlow = mediaDetailUseCase.invokeMovieDetails(
        movieId = tempMovieId,
    )

    private val movieImagesFlow = mediaImagesUseCase.invoke(
        mediaType = MediaType.Movie,
        mediaId = tempMovieId,
    )


    val movieDetailStateFlow: StateFlow<MovieDetailUiState> = combine(
        flow = mediaStatsFlow,
        flow2 = movieDetailFlow,
        flow3 = movieImagesFlow,
        transform = ::emitMovieDetailState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = MovieDetailUiState.Loading
    )

    fun toggleWatchList(value: Boolean) {
        viewModelScope.launch {
            watchListUseCase.invoke(
                movieId = tempMovieId,
                watchListOperation = WatchListOperation.Watchlist,
                wishList = value,
            ).collect {
                watchListUiState.value = when (it) {
                    is ResultX.Success -> WatchlistUiState.Success(value)
                    is ResultX.Error -> WatchlistUiState.Error(value)
                }
            }
        }
    }

    fun toggleFavourite(value: Boolean) {
        favouriteUiState.value = FavouriteUiState.Loading
        viewModelScope.launch {
            watchListUseCase.invoke(
                movieId = tempMovieId,
                watchListOperation = WatchListOperation.Favourites,
                wishList = value,
            ).collect {
                favouriteUiState.value = when (it) {
                    is ResultX.Success -> if (value) FavouriteUiState.AddedToFav else FavouriteUiState.RemovedFromFav
                    is ResultX.Error -> FavouriteUiState.Error(it.message)
                }
            }
        }
    }

//    fun addRating(value: Double) {
//        viewModelScope.launch {
//            rateMediaUseCase.invoke(
//                mediaType = MediaType.Movie,
//                mediaId = tempMovieId,
//                value = value,
//                ratingOperation = RatingOperation.AddRating
//            ).collect {
//                when (it) {
//                    is ResponseX.Loading -> RatingUiState.Loading
//                    is ResponseX.Success -> RatingUiState.Success(
//                        message = it.data?.statusMessage ?: ""
//                    )
//
//                    is ResponseX.Error -> RatingUiState.Error(it.message ?: "")
//                }
//            }
//        }
//    }

    private fun getMediaCredits() {
        viewModelScope.launch {
            movieCreditsUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = tempMovieId,
            ).collect {
                mediaCreditsStateFlow.value = when (it) {
                    is ResultX.Success -> CreditsUiState.Success(credits = it.data?.cast)
                    is ResultX.Error -> CreditsUiState.Error(it.message ?: "")
                }
            }
        }
    }

    private fun emitMovieDetailState(
        statsResponse: ResultX<MovieStats>,
        detailResponse: ResultX<MovieDetails>,
        imagesResponse: ResultX<MovieImages>
    ): MovieDetailUiState {
        if (statsResponse is ResultX.Success && detailResponse is ResultX.Success && imagesResponse is ResultX.Success) {
            getMediaCredits()
            if (statsResponse.data?.favorite == true) {
                favouriteUiState.value = FavouriteUiState.AddedToFav
            } else {
                favouriteUiState.value = FavouriteUiState.RemovedFromFav
            }

            statsResponse.data?.watchlist?.let {
                watchListUiState.value = WatchlistUiState.Success(successValue = it)
            }

            return MovieDetailUiState.MovieDetailsSuccess(
                data = detailResponse.data?.toPresentation(
                    movieStats = statsResponse.data, images = imagesResponse.data?.toImageList()
                )
            )
        } else if (statsResponse is ResultX.Error || detailResponse is ResultX.Error || imagesResponse is ResultX.Error) {
            return MovieDetailUiState.Error(message = "Something went wrong")
        }
        return MovieDetailUiState.Loading
    }

}