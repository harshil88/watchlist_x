package com.harshilpadsala.watchlistx.vm

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.repo.MovieRepo
import com.harshilpadsala.watchlistx.state.FailureState
import com.harshilpadsala.watchlistx.state.InitialState
import com.harshilpadsala.watchlistx.state.MovieListState
import com.harshilpadsala.watchlistx.state.SuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor(): ViewModel() {

    @Inject lateinit var movieRepo : MovieRepo

    private val _mutableState = mutableStateOf<MovieListState>(InitialState)
    val mutableState : State<MovieListState>
        get() = _mutableState

    fun fetchMovies(){
        viewModelScope.launch {
            kotlin.runCatching {
                movieRepo.getAllMovies()
            }.onSuccess {
                _mutableState.value = SuccessState(
                    response = it.body()
                )
            }
                .onFailure {
                    _mutableState.value = FailureState(
                        error = it.message
                    )
                }
        }
    }

}