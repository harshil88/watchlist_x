package com.harshilpadsala.watchlistx.constants

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun String.titleCase() = this[0].uppercase() + this.substring(1).lowercase()

fun Number.fromStatusToOperationType() : OperationStatus =
    when(this){
        1 -> OperationStatus.Created
        12 -> OperationStatus.Updated
        13 -> OperationStatus.Deleted
        else -> OperationStatus.Unknown
    }
