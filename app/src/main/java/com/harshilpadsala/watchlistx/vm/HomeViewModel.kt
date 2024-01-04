package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.detail.CardModel
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.data.res.list.TVContent
import com.harshilpadsala.watchlistx.data.res.list.toCardList
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.domain.usecase.GenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val discoverMovieUseCase: DiscoverMovieUseCase, private val genreUseCase: GenreUseCase

) : ViewModel() {


    val uiState = mutableStateOf(HomeUiState())

    init {
        uiState.value = HomeUiState(
            loading  = true,
        )
        nowPlaying()
    }

    fun onRefresh(){
        uiState.value = HomeUiState(
            refreshing = true,
        )
        nowPlaying()
    }


    private fun nowPlaying(){
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
}