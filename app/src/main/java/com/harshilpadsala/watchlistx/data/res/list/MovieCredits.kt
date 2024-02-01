package com.harshilpadsala.watchlistx.data.res.list

import com.harshilpadsala.watchlistx.data.res.detail.ActorDetails
import com.harshilpadsala.watchlistx.data.res.detail.toCardComponent
import com.harshilpadsala.watchlistx.data.res.model.CardModel

data class MovieCredits(
    val id : Int?,
    val cast : List<ActorDetails>?
)

fun MovieCredits.toCardList() : List<CardModel> =
    this.cast?.map {
        detail -> detail.toCardComponent()
    }?: listOf()
