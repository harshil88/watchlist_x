package com.harshilpadsala.watchlistx.vm

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MovieCategory
import com.harshilpadsala.watchlistx.constants.addX
import com.harshilpadsala.watchlistx.data.res.list.toListItemX
import com.harshilpadsala.watchlistx.data.res.model.FilterParams
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.domain.usecase.DiscoverMovieUseCase
import com.harshilpadsala.watchlistx.domain.usecase.FilterMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//todo : Learn more about saved state handle and configuration changes and recomposition

@Immutable
data class DiscoverUiState(
    val isLoading: Boolean? = null,
    val movies: List<ListItemXData>? = null,
    val currentPage: Int = 1,
    val hasReachedEnd: Boolean = false,
    val selectedMovieList: MovieCategory? = null,
    val isFailure: Boolean = false
)

@HiltViewModel
class DiscoverVM @Inject constructor(
    private val discoverMovieUseCase: DiscoverMovieUseCase,
    private val filterMoviesUseCase: FilterMoviesUseCase,
    val state: SavedStateHandle,
) : ViewModel() {

    val discoverUiState = mutableStateOf(DiscoverUiState())
    private var currentPage = 1


    private var filterParams: FilterParams? = null

    init {
        discoverMovieList()
    }

    fun callApi(updatedFilterParams: FilterParams?) {
        if (filterParams != updatedFilterParams) {
            filterParams = updatedFilterParams
            discoverUiState.value = DiscoverUiState()
            discoverMovieList()
        }
    }

    fun nextPage() {
        if (discoverUiState.value.isLoading == false) {
            discoverUiState.value = discoverUiState.value.copy(isLoading = true)
            discoverMovieList()
        }
    }


    private fun discoverMovieList() {
        viewModelScope.launch {
            filterMoviesUseCase.invoke(
                page = currentPage,
                dateGte = filterParams?.dateGte,
                dateLte = filterParams?.dateLte,
                sortBy = filterParams?.sortBy,
                withGenres = filterParams?.withGenres,
                withKeywords = filterParams?.withKeywords,
            ).collect {
                when (it) {
                    is ResultX.Success -> {
                        discoverUiState.value = discoverUiState.value.copy(
                            isLoading = false,
                            movies = discoverUiState.value.movies.addX(it.data?.toListItemX()),
                            currentPage = currentPage,
                            selectedMovieList = MovieCategory.NowPlaying,
                            hasReachedEnd = it.data?.totalPages == currentPage
                        )

                        if (!discoverUiState.value.hasReachedEnd) {
                            currentPage += 1
                        }
                    }

                    is ResultX.Error -> {
                        discoverUiState.value = discoverUiState.value.copy(
                            isLoading = false,
                            isFailure = true,
                            movies = null,
                        )
                    }
                }
            }
        }
    }


}