package com.harshilpadsala.watchlistx.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


sealed interface ResponseX<out T> {
    data class Success<T>(val data: T? = null) : ResponseX<T>
    data class Error(val message: String? = null) : ResponseX<Nothing>
     object Loading : ResponseX<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ResponseX<T>> {
    return this
        .map<T, ResponseX<T>> {
            ResponseX.Success(it)
        }
        .onStart { emit(ResponseX.Loading) }
        .catch { emit(ResponseX.Error(it.message)) }
}
