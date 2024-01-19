package com.harshilpadsala.watchlistx.data.res.list

import com.google.gson.annotations.SerializedName
import com.harshilpadsala.watchlistx.data.res.model.CardModel
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData

data class Content<T>(
    val page: Int?,
    val results: List<T>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

fun Content<MovieContent>.toCardList() : List<CardModel> =
    this.results?.map {
        it.toCardComponent()
    }?.toList()?: listOf()

fun Content<MovieContent>.toListItemX() : List<ListItemXData> =
    this.results?.map {
        it.toListItemX()
    }?.toList()?: listOf()





