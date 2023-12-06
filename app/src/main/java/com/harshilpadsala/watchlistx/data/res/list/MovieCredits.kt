package com.harshilpadsala.watchlistx.data.res.list

import com.harshilpadsala.watchlistx.data.res.detail.ActorDetails

data class MovieCredits(
    val id : Int?,
    val cast : List<ActorDetails>?
)
