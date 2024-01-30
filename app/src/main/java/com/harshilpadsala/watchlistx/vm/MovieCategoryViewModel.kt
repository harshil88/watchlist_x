package com.harshilpadsala.watchlistx.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.constants.addX
import com.harshilpadsala.watchlistx.constants.getMovieCategory
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.MoviesByCategoryUseCase
import com.harshilpadsala.watchlistx.navigation.ArgumentsX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MovieCategoryUiState(
    var isLoading: Boolean? = null,
    var movies: List<ListItemXData>? = null,
    var error: String? = null,
    var hasReachedEnd: Boolean = false,
    var currentPage: Int = 1,
)


@HiltViewModel
class MovieCategoryViewModel @Inject constructor(
    private val movieByCategoryUseCase: MoviesByCategoryUseCase,
    state: SavedStateHandle,
) : ViewModel() {
    var movieCategoryUiState by mutableStateOf(MovieCategoryUiState())

    private var currentPage = 1
    private var movieCategory = MovieCategory.NowPlaying


    init {
        movieCategory = state.get<String>(ArgumentsX.movieCategory).getMovieCategory()
        movieByCategory(movieCategory)
    }

    fun nextPage() {
        movieCategoryUiState = movieCategoryUiState.copy(isLoading = true)
        movieByCategory(movieCategory)
    }

    private fun movieByCategory(movieCategory: MovieCategory) {
        viewModelScope.launch {
            movieByCategoryUseCase.invoke(
                movieCategory = movieCategory, page = currentPage
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        movieCategoryUiState = movieCategoryUiState.copy(
                            isLoading = false,
                            movies = movieCategoryUiState.movies.addX(it.data?.toListItemX()),
                            currentPage = currentPage,
                            hasReachedEnd = it.data?.totalPages == currentPage,
                        )

                        if (!movieCategoryUiState.hasReachedEnd) {
                            currentPage += 1
                        }
                    }

                    is ResultX.Error -> {
                        movieCategoryUiState = movieCategoryUiState.copy(
                            error = it.message,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

}