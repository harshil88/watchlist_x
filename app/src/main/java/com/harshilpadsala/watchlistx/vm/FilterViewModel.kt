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
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FilterUiState(
    var isLoading : Boolean? = null,
    var error : String? = null,
    var genres : List<GenreContent>? = null,
    var keywords : List<KeywordContent>?= null,
)

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase,
    private val searchKeywordsUseCase: SearchKeywordsUseCase,
) : ViewModel() {

    val filterUiState = mutableStateOf(FilterUiState())
    init {
        genres()
    }

    fun genres(){
        viewModelScope.launch {
            genreUseCase.invoke().collect{
                when(it){
                    is ResultX.Success -> filterUiState.value = filterUiState.value.copy(
                        isLoading = false,
                        genres = it.data?.genres
                    )

                    is ResultX.Error -> filterUiState.value = filterUiState.value.copy(
                        isLoading = false,
                        error = it.message,
                    )
                }
            }
        }
    }

    fun searchKeywords(query : String){
        viewModelScope.launch {
            searchKeywordsUseCase.invoke(query).collect{
                when(it){
                    is ResultX.Success -> {
                        Log.i("SheetDebug" , "Reaching here at success")
                        filterUiState.value = filterUiState.value.copy(
                            keywords = it.data?.results
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