package com.harshilpadsala.watchlistx.constants

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import com.harshilpadsala.watchlistx.data.res.list.toListItemX

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
    this?.toMutableList()?.addAll(newElements ?: listOf())
    return this ?: newElements?: listOf()
}


