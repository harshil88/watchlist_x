package com.harshilpadsala.watchlistx.constants

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun String.titleCase() = this[0].uppercase() + this.substring(1).lowercase()

fun Number.fromStatusToOperationType(): OperationStatus = when (this) {
    1 -> OperationStatus.Created
    12 -> OperationStatus.Updated
    13 -> OperationStatus.Deleted
    else -> OperationStatus.Unknown
}


fun <T> List<T>?.addX(newElements: List<T>?): List<T> {
    val tempList = this?.toMutableList() ?: mutableListOf()
    tempList.addAll(newElements ?: listOf())
    return tempList.toList()
}

fun String?.getMovieCategory(): MovieCategory {
    MovieCategory.values().map { cat -> if (cat.name.equals(this, true)) return cat }
    return MovieCategory.NowPlaying
}

@OptIn(ExperimentalMaterialApi::class)
fun ModalBottomSheetState.hideX(scope : CoroutineScope){
    if(this.isVisible){
        scope.launch {
            this@hideX.hide()
        }
    }
}

fun Any.toEncodedUri(): String {
    val json = Gson().toJson(this)
    return Uri.encode(json)
}

inline fun <reified T> String.parseData() : T?{
    val decoded = Uri.decode(this)
    Log.i("DecodedJson" , decoded.toString())
    return Gson().fromJson(decoded , T::class.java)
}

