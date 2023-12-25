package com.harshilpadsala.watchlistx.vm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResponseX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverTvUseCase
import com.harshilpadsala.watchlistx.domain.usecase.SearchMovieUseCase
import com.harshilpadsala.watchlistx.state.DiscoverMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverVM @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val discoverMovieUseCase: DiscoverMovieUseCase,
    private val discoverTvUseCase: DiscoverTvUseCase,
) : ViewModel() {

    val selectedMediaType =
        mutableStateOf(MediaType.Movie)

    var isTabChanged = false


    val movieChipState =
        mutableStateOf(MovieList.Popular)

    val tvChipState =
        mutableStateOf(TvList.Popular)



    val popularMovieListSuccessState =
        mutableStateOf<DiscoverMovieUiState>(DiscoverMovieUiState.Initial)

    var currentPage = 1

    var data = mutableListOf<ListItemXData>()

    var resetCalled = false
    var isPageLoading = false

    init {
        discoverMovieList(MovieList.Popular)
    }

    fun reset(){
        resetCalled = true
        currentPage = 1
        data.clear()
    }

    fun onTabChange(mediaType: MediaType){
        reset()
        isTabChanged = true
        selectedMediaType.value = mediaType
        if (mediaType == MediaType.Movie) {
            discoverMovieList(movieChipState.value)
        } else {
            discoverTvList(tvChipState.value)

        }
    }

    fun toggleSheet(index: Int){
        reset()
        if (selectedMediaType.value == MediaType.Movie) {
            movieChipState.value = MovieList.values()[index]
            discoverMovieList(movieChipState.value)

        } else {
            discoverTvList(tvChipState.value)
            tvChipState.value = TvList.values()[index]
        }
    }

    fun shouldCallNextPage(){
        if(!isPageLoading){
            if(selectedMediaType.value == MediaType.Movie){
                discoverMovieList(movieChipState.value)
            }
            else{
                discoverTvList(tvChipState.value)
            }
        }
    }



    private fun discoverMovieList(movieList: MovieList) {
        isPageLoading = true
        viewModelScope.launch {
            discoverMovieUseCase.invoke(movieList, currentPage).collect {
                when (it) {
                    is ResponseX.Loading -> if(currentPage == 1){

                        popularMovieListSuccessState.value =
                            DiscoverMovieUiState.Loading
                    }

                    is ResponseX.Success -> {
                        isTabChanged = false
                        isPageLoading = false
                        val newElements = it.data?.results?.map { movie -> movie.toListItemX()}?.toList()?: listOf()
                        data.addAll(newElements)
                        popularMovieListSuccessState.value =
                        DiscoverMovieUiState.SuccessUiState(movies =  data , currentPage =  currentPage , mediaType = MediaType.Movie)
                        currentPage+=1
                    }

                    is ResponseX.Error ->{
                        isTabChanged = false
                        isPageLoading = false
                        popularMovieListSuccessState.value =

                        DiscoverMovieUiState.PopularMovieFailureUiState(message = it.message ?: "")
                }

                }
            }
        }
    }

    private fun discoverTvList(tvList: TvList) {
        isPageLoading = true
        viewModelScope.launch {
            discoverTvUseCase.invoke(tvList, currentPage).collect {
                when (it) {
                    is ResponseX.Loading -> if(currentPage == 1){

                        popularMovieListSuccessState.value =
                            DiscoverMovieUiState.Loading
                    }

                    is ResponseX.Success -> {

                        isPageLoading = false
                        val newElements = it.data?.results?.map { movie -> movie.toListItemX()}?.toList()?: listOf()
                        data.addAll(newElements)
                        popularMovieListSuccessState.value =
                            DiscoverMovieUiState.SuccessUiState(movies =  data ,  currentPage =  currentPage , mediaType = MediaType.Tv)
                        currentPage+=1
                    }

                    is ResponseX.Error -> {

                        isPageLoading = false
                        popularMovieListSuccessState.value =

                            DiscoverMovieUiState.PopularMovieFailureUiState(message = it.message ?: "")
                    }
                }
            }
        }
    }

    fun searchMovies(search: String) {
    }
}