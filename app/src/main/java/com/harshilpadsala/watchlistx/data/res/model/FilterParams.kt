package com.harshilpadsala.watchlistx.data.res.model

data class FilterParams(
    var dateGte: String? = null,
    var dateLte: String? = null,
    var sortBy: String? = null,
    var withGenres: String? = null,
    var withKeywords: String? = null,
    var voteAverageGte : Float? = null,
    var voteAverageLte : Float? = null,
    var voteCountGte : Float? = null,
    var voteCountLte : Float? = null,
    var withRuntimeGte : Int? = null,
    var withRuntimeLte : Int? = null,
)
