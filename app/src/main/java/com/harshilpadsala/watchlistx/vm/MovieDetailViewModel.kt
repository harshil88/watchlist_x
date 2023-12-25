package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.domain.usecase.AddToWatchListUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaAccountStatsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaCreditsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaDetailUseCase
import com.harshilpadsala.watchlistx.domain.usecase.RateMediaUseCase
import com.harshilpadsala.watchlistx.domain.usecase.RatingOperation
import com.harshilpadsala.watchlistx.domain.usecase.WatchListOperation
import com.harshilpadsala.watchlistx.state.WatchListUiState
import com.harshilpadsala.watchlistx.state.movie_detail.CreditsUiState
import com.harshilpadsala.watchlistx.state.movie_detail.MovieDetailUiState
import com.harshilpadsala.watchlistx.state.movie_detail.MovieStatsUiState
import com.harshilpadsala.watchlistx.state.movie_detail.RatingUiState
import com.harshilpadsala.watchlistx.state.movie_detail.WatchlistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    mediaStatsUseCase : MediaAccountStatsUseCase,
    mediaDetailUseCase: MediaDetailUseCase,
    private val watchListUseCase: AddToWatchListUseCase,
    private val rateMediaUseCase: RateMediaUseCase,
    private val movieCreditsUseCase : MediaCreditsUseCase,
) : ViewModel() {

    val watchListUiState = MutableStateFlow<WatchListUiState>(WatchListUiState.Loading)

    val favouriteUiState = MutableStateFlow<WatchListUiState>(WatchListUiState.Loading)

    var isAddedToWatchList = false
    var isAddedToFavourites = false

    val mediaStatsStateflow : StateFlow<MovieStatsUiState> = mediaStatsUseCase.invoke(
        mediaType = MediaType.Movie,
        mediaId = 901362,
    ).map {
        when(it){
            is ResponseX.Loading -> MovieStatsUiState.Loading
            is ResponseX.Success -> {
                MovieStatsUiState.Success(movieStats = it.data)
            }
            is ResponseX.Error -> MovieStatsUiState.Failure(it.message)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieStatsUiState.Loading,
    )


    val mediaDetailStateFlow : StateFlow<MovieDetailUiState> = mediaDetailUseCase.invokeMovieDetails(
        movieId = 901362,
    ).map {
        when(it){
            is ResponseX.Loading -> MovieDetailUiState.Loading
            is ResponseX.Success -> MovieDetailUiState.Success(data = it.data)
            is ResponseX.Error -> MovieDetailUiState.Error(it.message)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieDetailUiState.Loading,
    )

    fun toggleWatchList(){
         viewModelScope.launch {
             watchListUseCase.invoke(
                 mediaType = MediaType.Movie,
                 mediaId = 901362,
                 watchListOperation =    WatchListOperation.Watchlist,
                 wishList = isAddedToWatchList,
             ).collect{
                 when(it){
                     is ResponseX.Loading -> WatchListUiState.Loading
                     is ResponseX.Success -> WatchlistUiState.WatchlistSuccess(message = it.data ?: "")
                     is ResponseX.Error -> MovieDetailUiState.Error(it.message)
                 }
             }
         }
    }

    fun toggleFavourite(){
        viewModelScope.launch {
             watchListUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = 901362,
                watchListOperation =    WatchListOperation.Favourites,
                wishList = isAddedToFavourites,
            ).collect{
                 when(it){
                    is ResponseX.Loading ->  WatchListUiState.Loading
                    is ResponseX.Success -> WatchlistUiState.FavouriteSuccess(message = it.data ?: "")
                    is ResponseX.Error -> MovieDetailUiState.Error(it.message)
                }
            }
        }
    }

    fun addRating(value : Double){
        viewModelScope.launch {
            rateMediaUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = 901362,
                value = value,
                ratingOperation = RatingOperation.AddRating
            ).collect{
                when(it){
                    is ResponseX.Loading -> RatingUiState.Loading
                    is ResponseX.Success -> RatingUiState.Success(message = it.data?.statusMessage?:"")
                    is ResponseX.Error -> RatingUiState.Error(it.message ?: "")
                }
            }
        }
    }

    fun getMediaCredits(){
        viewModelScope.launch {
            movieCreditsUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = 901362,
            ).collect{
                when(it){
                    is ResponseX.Loading -> CreditsUiState.Loading
                    is ResponseX.Success -> CreditsUiState.Success(credits = it.data?.cast)
                    is ResponseX.Error -> CreditsUiState.Error(it.message ?: "")
                }
            }
        }
    }

}