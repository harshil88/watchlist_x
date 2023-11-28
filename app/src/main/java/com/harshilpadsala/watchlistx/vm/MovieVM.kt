package com.harshilpadsala.watchlistx.vm

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshilpadsala.watchlistx.repo.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor(): ViewModel() {

    @Inject lateinit var movieRepo : MovieRepo

    fun fetchMovies(){
        viewModelScope.launch {
            kotlin.runCatching {
                movieRepo.getAllMovies()
            }.onSuccess {
                Log.i("MovieDB" , "MovieSuccess ${it.body()}")
            }
                .onFailure {
                    Log.i("MovieDB" , "MovieSuccess ${it.message}")

                }
        }
    }

}