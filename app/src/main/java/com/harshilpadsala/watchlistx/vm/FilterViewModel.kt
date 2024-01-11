package com.harshilpadsala.watchlistx.vm

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FilterUiState(
    var isLoading: Boolean? = null,
    var error: String? = null,
    var genres: List<GenreContent>? = null,
    var searchKeywords: List<KeywordContent>? = null,
    var selectedKeywords: List<KeywordContent> = listOf(),
    )

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase,
    private val searchKeywordsUseCase: SearchKeywordsUseCase,
) : ViewModel() {

    val filterUiState = mutableStateOf(FilterUiState())
    private lateinit var searchQueryFlow: Flow<String>

    @OptIn(FlowPreview::class)
    private var saQueryFlow = MutableStateFlow<String>("")

    init {
        genres()
        initTemporarySearchCollector()
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
            saQueryFlow.value = query
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

    fun removeUnselectedKeywords() {

    }

    @OptIn(FlowPreview::class)
    fun initTemporarySearchCollector() {
        viewModelScope.launch {
            saQueryFlow.debounce(1000).collect {
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