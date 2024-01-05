package com.harshilpadsala.watchlistx.data.res.detail

import android.util.Log
import com.google.gson.Gson

// todo : Optimization :  Find a proper way to parse data class conditionally

data class MovieStats(
    val id: Int?,
    val favorite: Boolean?,
    val rated: Any?,
    val watchlist: Boolean?,
    val isRated: Boolean?,
    val ratings: Rated?,
)

fun MovieStats.parseRating(): MovieStats {
    if (this.rated is Boolean) {
        return this.copy(
            isRated = false,
        )
    } else if (this.rated is Map<*, *>) {
        val rated = Gson().fromJson(this.rated.toString(), Rated::class.java)
        return this.copy(
            isRated = true,
            ratings = rated,
        )
    }

    return this.copy()
}


data class Rated(
    val value: Double?
)
