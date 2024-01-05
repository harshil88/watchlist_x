package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.detail.MovieStats
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.data.res.list.TVContent
import com.harshilpadsala.watchlistx.data.res.list.toCardList
import com.harshilpadsala.watchlistx.data.res.model.CardModel
import com.harshilpadsala.watchlistx.domain.usecase.AddToWatchListUseCase
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.domain.usecase.GenreUseCase
import com.harshilpadsala.watchlistx.domain.usecase.MediaAccountStatsUseCase
import com.harshilpadsala.watchlistx.domain.usecase.WatchListOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//todo : See that movie lists api call can be furthur optimized


//todo : Make extension function to stop loading or refreshikng

data class HomeUiState(
    var loading: Boolean? = null,
    var refreshing: Boolean = false,
    var showError: Boolean? = null,
    var popularMovieList: List<CardModel>? = null,
    var topRatedMovieList: List<CardModel>? = null,
    var genres: List<GenreContent>? = null,
    var nowPlayingMovieList: List<CardModel>? = null,
    var upcomingMovieList: List<CardModel>? = null,
    var discoverTvList: List<TVContent>? = null,
)

data class MovieStatsUiState(
    var loading: Boolean? = null,
    var movieStats: MovieStats? = null,
    var selectedMovieDetail: CardModel? = null,
    var successMessage: String? = null,
    var errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val discoverMovieUseCase: DiscoverMovieUseCase,
    private val genreUseCase: GenreUseCase,
    private val movieStatsUseCase: MediaAccountStatsUseCase,
    private val addToWatchListUseCase: AddToWatchListUseCase
) : ViewModel() {


    val uiState = mutableStateOf(HomeUiState())
    val movieStatsUiState = mutableStateOf(MovieStatsUiState())

    init {
        uiState.value = HomeUiState(
            loading = true,
        )
        nowPlaying()
    }

    fun onRefresh() {
        uiState.value = HomeUiState(
            refreshing = true,
        )
        nowPlaying()
    }

    fun resetMovieStats() {
        movieStatsUiState.value = MovieStatsUiState()
    }

    private fun nowPlaying() {
        viewModelScope.launch {
            discoverMovieUseCase.invoke(
                movieList = MovieList.NowPlaying,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState.value = uiState.value.copy(
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
                movieList = MovieList.Popular,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState.value = uiState.value.copy(
                            popularMovieList = it.data?.toCardList(),
                            showError = false,
                            loading = false,
                            refreshing = false,
                        )
                        genres()
                    }

                    is ResultX.Error -> {
                        uiState.value = uiState.value.copy(

                        )
                        genres()
                    }
                }
            }
        }
    }

    private fun genres() {
        viewModelScope.launch {
            genreUseCase.invoke(
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState.value = uiState.value.copy(
                            genres = it.data?.genres,
                            showError = false,
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
                movieList = MovieList.TopRated,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState.value = uiState.value.copy(
                            topRatedMovieList = it.data?.toCardList(),
                            showError = false,
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
                movieList = MovieList.Upcoming,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        uiState.value = uiState.value.copy(
                            upcomingMovieList = it.data?.toCardList(),
                            showError = false,
                        )
                    }

                    is ResultX.Error -> {}
                }
            }
        }
    }

    fun movieStats(cardModel: CardModel) {
        movieStatsUiState.value = MovieStatsUiState(loading = true)
        viewModelScope.launch {
            movieStatsUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = cardModel.id ?: 0,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        movieStatsUiState.value = movieStatsUiState.value.copy(
                            movieStats = it.data,
                            loading = false,
                            selectedMovieDetail = cardModel
                        )
                    }

                    is ResultX.Error -> {}
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
                when (it) {
                    is ResultX.Success -> {
                        movieStatsUiState.value = movieStatsUiState.value.copy(
                            successMessage = it.data?.statusMessage
                        )
                    }

                    is ResultX.Error -> {

                    }
                }

            }
        }
    }


    fun wishList(wishList: Boolean, movieId: Int) {
        movieStatsUiState.value = MovieStatsUiState(loading = true)
        viewModelScope.launch {
            addToWatchListUseCase.invoke(
                movieId = movieId,
                watchListOperation = WatchListOperation.Watchlist,
                wishList = wishList,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        movieStatsUiState.value = movieStatsUiState.value.copy(
                            loading = false,
                            successMessage = it.data?.statusMessage
                        )
                    }

                    is ResultX.Error -> {}
                }
            }
        }
    }
}