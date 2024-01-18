package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent
import com.harshilpadsala.watchlistx.data.res.model.FilterParams
import com.harshilpadsala.watchlistx.domain.usecase.FilterMoviesUseCase
import com.harshilpadsala.watchlistx.domain.usecase.GenreUseCase
import com.harshilpadsala.watchlistx.domain.usecase.SearchKeywordsUseCase
import com.harshilpadsala.watchlistx.navigation.filterNavArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject


const val SEARCH_DEBOUNCING_DURATION = 1000L




data class FilterUiState(
    var isLoading: Boolean? = null,
    var error: String? = null,
    var genres: List<GenreContent>? = null,
    var selectedGenres : List<GenreContent> = listOf(),
    var searchKeywords: List<KeywordContent>? = null,
    var selectedKeywords: List<KeywordContent> = listOf(),
    var shouldShowDatePicker: Boolean = false,
    var keywordSearchString: String = "",
)

fun List<GenreContent>.getGenderParams(): String {
    val genderIds = this.map { gender -> gender.id }.toList()
    return genderIds.joinToString(separator = ",")
}

fun List<KeywordContent>.getKeywordParams(): String {
    val keywordIds = this.map { keyword -> keyword.id }.toList()
    return keywordIds.joinToString(separator = "|")
}


@HiltViewModel
class FilterViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase,
    private val searchKeywordsUseCase: SearchKeywordsUseCase,
    private val filterMoviesUseCase: FilterMoviesUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

     var filterUiState by mutableStateOf(FilterUiState())
     val selectedFilterParams = FilterParams()


    private var searchQueryFlow = MutableStateFlow("")

    init {
        genres()
        searchQueryCollector()
    }

    fun genres() {
        viewModelScope.launch {
            genreUseCase.invoke().collect {
                filterUiState = when (it) {
                    is ResultX.Success -> filterUiState.copy(
                        isLoading = false, genres = it.data?.genres
                    )

                    is ResultX.Error -> filterUiState.copy(
                        isLoading = false,
                        error = it.message,
                    )
                }
            }
        }
    }

    fun searchKeywordsWithDebouncing(query: String) {
        viewModelScope.launch {
            searchQueryFlow.value = query
        }
    }

    fun addKeyword(keywordContent: KeywordContent) {
        if (!filterUiState.selectedKeywords.contains(keywordContent)) {
            val newSelectedKeywordsList = filterUiState.selectedKeywords.toMutableList()
            newSelectedKeywordsList.add(0, keywordContent)
            filterUiState = filterUiState.copy(
                selectedKeywords = newSelectedKeywordsList.toList(), searchKeywords = listOf()
            )
        }
    }

    fun updateKeywordListSelection(keywords : List<KeywordContent>){
        filterUiState = filterUiState.copy(
            isLoading = true
        )
        filterUiState = filterUiState.copy(
            selectedKeywords = keywords,
            isLoading = false
        )
    }

    @OptIn(FlowPreview::class)
    fun searchQueryCollector() {
        viewModelScope.launch {
            searchQueryFlow.debounce(SEARCH_DEBOUNCING_DURATION).collect {
                searchKeywords(it)
            }
        }
    }

    private fun searchKeywords(query: String) {
        viewModelScope.launch {
            searchKeywordsUseCase.invoke(query).collect {
                when (it) {
                    is ResultX.Success -> {
                        filterUiState = filterUiState.copy(
                            searchKeywords = it.data?.results
                        )
                    }

                    is ResultX.Error -> filterUiState = filterUiState.copy(
                        error = it.message,
                    )
                }
            }
        }
    }

    private fun applyFilters(query: String) {
        viewModelScope.launch {
            searchKeywordsUseCase.invoke(query).collect {
                when (it) {
                    is ResultX.Success -> {
                        filterUiState = filterUiState.copy(
                            searchKeywords = it.data?.results
                        )
                    }

                    is ResultX.Error -> filterUiState = filterUiState.copy(
                        error = it.message,
                    )
                }
            }
        }
    }
}