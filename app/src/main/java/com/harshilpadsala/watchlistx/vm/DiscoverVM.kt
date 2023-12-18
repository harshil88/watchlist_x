package com.harshilpadsala.watchlistx.vm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverTvUseCase
import com.harshilpadsala.watchlistx.domain.usecase.SearchMovieUseCase
import com.harshilpadsala.watchlistx.state.DiscoverMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverVM @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val discoverMovieUseCase: DiscoverMovieUseCase,
    private val discoverTvUseCase: DiscoverTvUseCase,
) : ViewModel() {


    val popularMovieListSuccessState =
        mutableStateOf<DiscoverMovieUiState>(DiscoverMovieUiState.Initial)
    var currentPage = 1

    var data = mutableListOf<ListItemXData>()

    init {
        discoverMovieList(MovieList.Popular)
    }

    fun discoverMovieList(movieList: MovieList) {
        viewModelScope.launch {
            discoverMovieUseCase.invoke(movieList, currentPage).collect {
                when (it) {
                    is ResponseX.Loading -> if(currentPage == 1){
                        popularMovieListSuccessState.value =
                            DiscoverMovieUiState.Loading
                    }

                    is ResponseX.Success -> {
                        currentPage+=1
                        Log.i("InfiniteDebug" , "This is the current page $currentPage")
                        Log.i("InfiniteDebug" , "This are the movies Imp data ${it.data?.page} ${it.data?.results?.size}")


                        val newElements = it.data?.results?.map { movie -> movie.toListItemX()}?.toList()?: listOf()
                        data.addAll(newElements)
                        Log.i("InfiniteDebug" , "Number of items in data ${data.size}")

                        popularMovieListSuccessState.value =
                        DiscoverMovieUiState.SuccessUiState(movies =  data)}

                    is ResponseX.Error -> popularMovieListSuccessState.value =
                        DiscoverMovieUiState.PopularMovieFailureUiState(message = it.message ?: "")
                }
            }
        }
    }

    fun discoverTvList(tvList: TvList) {
        viewModelScope.launch {
            discoverTvUseCase.invoke(tvList, 1).collect {
                when (it) {
                    is ResponseX.Loading -> popularMovieListSuccessState.value =
                        DiscoverMovieUiState.Loading

                    is ResponseX.Success -> popularMovieListSuccessState.value =
                        DiscoverMovieUiState.SuccessUiState(movies = it.data?.results?.map { tvShow -> tvShow.toListItemX() }
                            ?: listOf())

                    is ResponseX.Error -> popularMovieListSuccessState.value =
                        DiscoverMovieUiState.PopularMovieFailureUiState(message = it.message ?: "")
                }
            }
        }
    }

    fun searchMovies(search: String) {
    }
}