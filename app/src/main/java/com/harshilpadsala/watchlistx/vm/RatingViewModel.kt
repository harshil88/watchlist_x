package com.harshilpadsala.watchlistx.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class Rated(
    var movieName: String? = null,
    var isRated: Boolean? = null,
    var ratings: Double? = null,
)

sealed interface ChangeRating {
    object Loading : ChangeRating
}


@HiltViewModel
class RatingViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {

    val ratingState = mutableStateOf(
        Rated(
            movieName = state.get<String>("movieName"),
            isRated = state.get<Boolean>("isRated"),
            ratings = state.get<Float>("ratings")?.toDouble()
        )
    )

}