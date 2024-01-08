package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.navigation.movieListTypeArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

//todo : Learn more about saved state handle and configuration changes and recomposition

data class DiscoverUiState(
    var isLoading : Boolean? = null,
    var movies : MutableList<ListItemXData>?= null,
    var currentPage : Int = 1,
    var hasReachedEnd : Boolean = false,
    var selectedMovieList : MovieList? = null,
    var isFailure : Boolean = false
){

}

@HiltViewModel
class DiscoverVM @Inject constructor(
    private val discoverMovieUseCase: DiscoverMovieUseCase,
    val state : SavedStateHandle,
) : ViewModel() {

    val discoverUiState = mutableStateOf(DiscoverUiState())





    val movieChipState =
        mutableStateOf(MovieList.Popular)






    init {
        val selectedMovieList = state.get<MovieList>(movieListTypeArg)
        if(selectedMovieList!=null){
            discoverMovieList(selectedMovieList)
        }

    }


//    fun toggleSheet(index: Int){
//        if (selectedMediaType.value == MediaType.Movie) {
//            movieChipState.value = MovieList.values()[index]
//            discoverMovieList(movieChipState.value)
//        }
//    }

    fun nextPage(){
        if(discoverUiState.value.isLoading == false){
            discoverUiState.value = discoverUiState.value.copy(isLoading = true)
            discoverMovieList(movieList = MovieList.NowPlaying)
        }
    }



    private fun discoverMovieList(movieList: MovieList) {

        viewModelScope.launch {
            delay(2000)
            discoverMovieUseCase.invoke(movieList, discoverUiState.value.currentPage).collect {
                when (it) {

                    is ResultX.Success -> {
                        val alreadyPresentMovies = discoverUiState.value.movies
                        val newMovies = it.data?.results?.map {item -> item.toListItemX() }?.toMutableList()?: mutableListOf()

                        alreadyPresentMovies?.addAll(newMovies)

                        val updatedPage = discoverUiState.value.currentPage + 1

                        discoverUiState.value = discoverUiState.value.copy(
                            isLoading = false,
                            movies = alreadyPresentMovies ?: newMovies,
                            currentPage = updatedPage,
                            selectedMovieList = movieList,
                        )

                    }

                    is ResultX.Error ->{
                        discoverUiState.value = discoverUiState.value.copy(
                            isLoading = false,
                            isFailure = true,
                        )
                }

                }
            }
        }
    }




}