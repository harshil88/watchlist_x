package com.harshilpadsala.watchlistx.base


sealed interface ResultX<out T> {
    data class Success<T>(val data: T? = null) : ResultX<T>
    data class Error(val message: String? = null) : ResultX<Nothing>

}

//fun <T> Flow<T>.asResult(): Flow<ResponseX<T>> {
//    return this
//        .map<T, ResponseX<T>> {
//            ResponseX.Success(it)
//        }
//        .onStart { emit(ResponseX.Loading) }
//        .catch { emit(ResponseX.Error(it.message)) }
//}

