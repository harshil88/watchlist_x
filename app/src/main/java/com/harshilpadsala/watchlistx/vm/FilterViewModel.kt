package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent
import com.harshilpadsala.watchlistx.domain.usecase.GenreUseCase
import com.harshilpadsala.watchlistx.domain.usecase.SearchKeywordsUseCase
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
    var searchKeywords: List<KeywordContent>? = null,
    var selectedKeywords: List<KeywordContent> = listOf(),
    var shouldShowDatePicker : Boolean = false,
    var keywordSearchString : String = "",
    )

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase,
    private val searchKeywordsUseCase: SearchKeywordsUseCase,
) : ViewModel() {

    val filterUiState = mutableStateOf(FilterUiState())

    private var searchQueryFlow = MutableStateFlow("")

    init {
        genres()
        searchQueryCollector()
    }

    fun genres() {
        viewModelScope.launch {
            genreUseCase.invoke().collect {
                when (it) {
                    is ResultX.Success -> filterUiState.value = filterUiState.value.copy(
                        isLoading = false, genres = it.data?.genres
                    )

                    is ResultX.Error -> filterUiState.value = filterUiState.value.copy(
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
        if (!filterUiState.value.selectedKeywords.contains(keywordContent)) {
            val newSelectedKeywordsList = filterUiState.value.selectedKeywords.toMutableList()
            newSelectedKeywordsList.add(0, keywordContent)
            filterUiState.value = filterUiState.value.copy(
                selectedKeywords = newSelectedKeywordsList.toList(), searchKeywords = listOf()
            )
        }
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
                        filterUiState.value = filterUiState.value.copy(
                            searchKeywords = it.data?.results
                        )
                    }

                    is ResultX.Error -> filterUiState.value = filterUiState.value.copy(
                        error = it.message,
                    )
                }
            }
        }
    }
}