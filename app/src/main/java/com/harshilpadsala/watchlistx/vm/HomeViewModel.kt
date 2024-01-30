package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.list.TVContent
import com.harshilpadsala.watchlistx.data.res.list.toCardList
import com.harshilpadsala.watchlistx.data.res.model.CardModel
import com.harshilpadsala.watchlistx.domain.usecase.AddToWatchListUseCase
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaAccountStatsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.WatchListOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



data class HomeUiState(
    var loading: Boolean? = null,
    var refreshing: Boolean = false,
    var showError: Boolean? = null,
    var popularMovieList: List<CardModel>? = null,
    var topRatedMovieList: List<CardModel>? = null,
    var nowPlayingMovieList: List<CardModel>? = null,
    var upcomingMovieList: List<CardModel>? = null,
    var discoverTvList: List<TVContent>? = null,
    var errorMessage: String? = null

)

data class MovieStatsUiState(
    var loading: Boolean? = null,
    var movieStats: MovieStats? = null,
    var selectedMovieDetail: CardModel? = null,
    var message: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val discoverMovieUseCase: DiscoverMovieUseCase,
    private val movieStatsUseCase: MediaAccountStatsUseCase,
    private val addToWatchListUseCase: AddToWatchListUseCase
) : ViewModel() {


    var uiState by mutableStateOf(HomeUiState())
    var movieStatsUiState by mutableStateOf(MovieStatsUiState())

    init {
        startApiCall()
    }

    fun startApiCall() {
        uiState = HomeUiState(
            loading = true,
        )
        nowPlaying()
    }

    fun onRefresh() {
        uiState = HomeUiState(
            refreshing = true,
        )
        nowPlaying()
    }

    fun resetMovieStats() {
        movieStatsUiState = MovieStatsUiState()
    }

    private fun nowPlaying() {
        viewModelScope.launch {
            discoverMovieUseCase.invoke(
                movieList = MovieCategory.NowPlaying,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            nowPlayingMovieList = it.data?.toCardList(),
                            loading = false,
                            refreshing = false,
                            showError = false,
                        )
                        popularMovies()
                    }

                    is ResultX.Error -> {
                        popularMovies()
                    }
                }
            }
        }
    }

    private fun popularMovies() {
        viewModelScope.launch {
            discoverMovieUseCase.invoke(
                movieList = MovieCategory.Popular,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            popularMovieList = it.data?.toCardList(),
                            showError = false,
                            loading = false,
                            refreshing = false,
                        )
                        topRatedMovies()
                    }

                    is ResultX.Error -> {
                        topRatedMovies()
                    }
                }
            }
        }
    }


    private fun topRatedMovies() {
        viewModelScope.launch {
            discoverMovieUseCase.invoke(
                movieList = MovieCategory.TopRated,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            topRatedMovieList = it.data?.toCardList(),
                            showError = false,
                            loading = false,
                            refreshing = false,
                        )
                        upcomingMovies()
                    }

                    is ResultX.Error -> {
                        upcomingMovies()
                    }
                }
            }
        }
    }

    private fun upcomingMovies() {
        viewModelScope.launch {
            discoverMovieUseCase.invoke(
                movieList = MovieCategory.Upcoming,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState = uiState.copy(
                            upcomingMovieList = it.data?.toCardList(),
                            loading = false,
                            refreshing = false,
                            showError = false,
                        )

                    }

                    is ResultX.Error -> {
                        if (uiState.showError == null) {
                            uiState = uiState.copy(
                                showError = true,
                                loading = false,
                                refreshing = false,
                                errorMessage = "Something Went Wrong"
                            )
                        }
                    }
                }
            }
        }
    }

    fun movieStats(cardModel: CardModel) {
        movieStatsUiState = MovieStatsUiState(loading = true)
        viewModelScope.launch {
            movieStatsUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = cardModel.id ?: 0,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        movieStatsUiState = movieStatsUiState.copy(
                            movieStats = it.data,
                            loading = false,
                            selectedMovieDetail = cardModel,
                        )
                    }

                    is ResultX.Error -> {
                        movieStatsUiState = movieStatsUiState.copy(
                            loading = false,
                            message = it.message,
                        )
                    }
                }
            }
        }
    }

    fun favourite(favourite: Boolean, movieId: Int) {
        viewModelScope.launch {
            addToWatchListUseCase.invoke(
                movieId = movieId,
                watchListOperation = WatchListOperation.Favourites,
                wishList = favourite,

                ).collect {
                movieStatsUiState = when (it) {
                    is ResultX.Success -> {
                        movieStatsUiState.copy(
                            message = it.data?.statusMessage
                        )
                    }

                    is ResultX.Error -> {
                        movieStatsUiState.copy(
                            message = it.message,
                        )
                    }
                }

            }
        }
    }


    fun wishList(wishList: Boolean, movieId: Int) {
        viewModelScope.launch {
            addToWatchListUseCase.invoke(
                movieId = movieId,
                watchListOperation = WatchListOperation.Watchlist,
                wishList = wishList,
            ).collect {
                movieStatsUiState = when (it) {
                    is ResultX.Success -> {
                        movieStatsUiState.copy(
                            message = it.data?.statusMessage
                        )
                    }

                    is ResultX.Error -> {
                        movieStatsUiState.copy(
                            message = it.message,
                        )
                    }
                }
            }
        }
    }
}