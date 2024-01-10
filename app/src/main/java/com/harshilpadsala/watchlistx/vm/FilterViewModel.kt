package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.domain.usecase.GenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FilterUiState(
    var isLoading : Boolean? = null,
    var error : String? = null,
    var genres : List<GenreContent>? = null,
)

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase
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

}