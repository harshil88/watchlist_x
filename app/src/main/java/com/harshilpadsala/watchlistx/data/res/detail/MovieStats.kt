package com.harshilpadsala.watchlistx.data.res.detail

//todo : Problem of Manually Parsing rated can be a Boolean or can be parsed to Rated Class


data class MovieStats(
    val id: Int?, val favorite: Boolean?, val rated: Boolean?, val watchlist: Boolean?
)

data class Rated(
    val value : Double?
)
