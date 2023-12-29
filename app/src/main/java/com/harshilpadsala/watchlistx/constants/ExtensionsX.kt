package com.harshilpadsala.watchlistx.constants

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun String.titleCase() = this[0].uppercase() + this.substring(1).lowercase()