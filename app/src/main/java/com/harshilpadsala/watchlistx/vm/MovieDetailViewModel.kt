package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.detail.MovieDetails
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.data.res.list.toCardList
import com.harshilpadsala.watchlistx.data.res.list.toImageList
import com.harshilpadsala.watchlistx.data.res.model.CardModel
import com.harshilpadsala.watchlistx.domain.usecase.AddToWatchListUseCase
import com.harshilpadsala.watchlistx.domain.usecase.GenreUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaAccountStatsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaCreditsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaDetailUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaImagesUseCase
import com.harshilpadsala.watchlistx.domain.usecase.WatchListOperation
import com.harshilpadsala.watchlistx.navigation.ArgumentsX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MovieDetailUiState(
    var isLoading: Boolean? = null,
    var error: String? = null,
    var genres: List<GenreContent>? = null,
    var images: List<String>? = null,
    var details: MovieDetails? = null,
    var stats: MovieStats? = null,
    var credits: List<CardModel>? = null,
    var favMessage: String? = null,
    var isFavourite : Boolean? = null,
    var isWatchList : Boolean? = null,
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val mediaStatsUseCase: MediaAccountStatsUseCase,
    private val movieDetailUseCase: MediaDetailUseCase,
    private val mediaImagesUseCase: MediaImagesUseCase,
    private val genreUseCase: GenreUseCase,
    private val watchListUseCase: AddToWatchListUseCase,
    private val movieCreditsUseCase: MediaCreditsUseCase,
    state: SavedStateHandle
) : ViewModel() {

    var movieId = 0

    init {
        movieId = state.get<Int>(ArgumentsX.movieId) ?: 0
        details()
    }

    var uiState by mutableStateOf(MovieDetailUiState())

    fun reset(){
        uiState = uiState.copy(
            favMessage = null
        )
    }

    private fun images() {
        viewModelScope.launch {
            mediaImagesUseCase.invoke(
                mediaId = movieId
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            isLoading = false, images = it.data?.toImageList()
                        )
                        genres()
                    }

                    is ResultX.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = it.message,
                        )
                        genres()
                    }
                }
            }
        }
    }

    private fun details() {
        viewModelScope.launch {
            movieDetailUseCase.invoke(
                movieId = movieId
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            isLoading = false, details = it.data
                        )
                        images()
                    }

                    is ResultX.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = it.message,
                        )
                        images()

                    }
                }
            }
        }
    }

   private fun genres() {
        viewModelScope.launch {
            genreUseCase.invoke().collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            isLoading = false, genres = it.data?.genres
                        )
                        stats()
                    }

                    is ResultX.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = it.message,
                        )
                        stats()
                    }
                }
            }
        }
    }

    private fun stats() {
        viewModelScope.launch {
            mediaStatsUseCase.invoke(
                movieId = movieId
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            isLoading = false, stats = it.data,
                            isFavourite = it.data?.favorite,
                            isWatchList = it.data?.watchlist
                        )
                        credits()
                    }

                    is ResultX.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = it.message,
                        )
                        credits()

                    }
                }
            }
        }
    }

    private fun credits() {
        viewModelScope.launch {
            movieCreditsUseCase.invoke(
                movieId = movieId
            ).collect {
                uiState = when (it) {
                    is ResultX.Success -> {
                        uiState.copy(
                            isLoading = false, credits = it.data?.toCardList()
                        )
                    }

                    is ResultX.Error -> {
                        uiState.copy(
                            isLoading = false,
                            error = it.message,
                        )

                    }
                }
            }
        }
    }


    fun toggleWatchList(value: Boolean) {
        viewModelScope.launch {
            watchListUseCase.invoke(
                movieId = movieId,
                watchListOperation = WatchListOperation.Watchlist,
                wishList = value,
            ).collect {
                uiState = when (it) {
                    is ResultX.Success -> uiState.copy(
                        isLoading = false,
                        isWatchList = value,
                        favMessage = it.toString()
                    )

                    is ResultX.Error -> uiState.copy(isLoading = false, favMessage = it.toString())
                }
            }
        }
    }

    fun toggleFavourite(value: Boolean) {
        viewModelScope.launch {
            watchListUseCase.invoke(
                movieId = movieId,
                watchListOperation = WatchListOperation.Favourites,
                wishList = value,
            ).collect {
                uiState = when (it) {
                    is ResultX.Success -> uiState.copy(
                        isLoading = false,
                        isFavourite = value,
                        favMessage = it.toString()
                    )

                    is ResultX.Error -> uiState.copy(isLoading = false, favMessage = it.toString())
                }
            }
        }
    }

}


