package com.harshilpadsala.watchlistx.data.res.list

data class GenreList(
    val genres : List<GenreContent>?
)

data class GenreContent(
    val id : Int?,
    val name : String?
)