package com.harshilpadsala.watchlistx.vm

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.harshilpadsala.watchlistx.base.ResultX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.parseData
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.data.res.model.toRatingUiState
import com.harshilpadsala.watchlistx.domain.usecase.RateMediaUseCase
import com.harshilpadsala.watchlistx.domain.usecase.RatingOperation
import com.harshilpadsala.watchlistx.navigation.ArgumentsX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RatingUiState(
    var isRated : Boolean? = false,
    var currentRating : Int? = null,
    var isLoading : Boolean? = null,
    var movieName : String? = null,
    var movieId : Int? =  null,
    var posterPath : String? = null,
    var success : String? = null,
    var failure : String? = null,
)
@HiltViewModel
class RatingViewModel @Inject constructor(
    state: SavedStateHandle,
    private val rateMediaUseCase: RateMediaUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(RatingUiState())
    private val ratingArgsModel: RatingArgsModel

    init {
        val ratingJson = state.get<String>(ArgumentsX.ratingJson)
        ratingArgsModel = ratingJson?.parseData<RatingArgsModel>()?: RatingArgsModel()
        uiState = ratingArgsModel.toRatingUiState()
    }

    fun submitRatings(ratings: Int) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            rateMediaUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = ratingArgsModel.movieId ?: 0,
                ratingOperation = RatingOperation.AddRating,
                value = ratings,
            ).collect {
                uiState = when (it) {
                    is ResultX.Success -> uiState.copy(success = it.data?.statusMessage , isLoading = false)
                    is ResultX.Error -> uiState.copy(failure = it.message)
                }
            }
        }
    }

    fun deleteRatings() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            rateMediaUseCase.invoke(
                mediaType = MediaType.Movie,
                mediaId = ratingArgsModel.movieId ?: 0,
                ratingOperation = RatingOperation.DeleteRating,
            ).collect {
                uiState = when (it) {
                    is ResultX.Success -> uiState.copy(success = it.data?.statusMessage , isLoading = false)
                    is ResultX.Error -> uiState.copy(failure = it.message)
                }
            }
        }
    }

}