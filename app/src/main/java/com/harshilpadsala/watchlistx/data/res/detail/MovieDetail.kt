package com.harshilpadsala.watchlistx.data.res.detail

import com.google.gson.annotations.SerializedName
import com.harshilpadsala.watchlistx.data.Genre

data class MovieDetails(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: CollectionDetails?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date") val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)


data class ProductionCompany(
    val id: Int?,
    @SerializedName("logo_path") val logoPath: String?,
    val name: String?,
    @SerializedName("origin_country") val originCountry: String?
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso31661: String?, val name: String?
)

data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String?,
    @SerializedName("iso_639_1") val iso6391: String?,
    val name: String?
)

data class CollectionDetails(
    val id: Int?,
    val name: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?
)

data class MovieDetailPresenter(
    val id: Int?,
    val title: String?,
    val releaseDate: String?,
    val overview: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val genres: List<Genre>?,
    val images : List<String>?,
    val movieStats: MovieStats?,
)

fun MovieDetails.toPresentation(movieStats: MovieStats? = null , images : List<String>?): MovieDetailPresenter =
    MovieDetailPresenter(
        id, title, releaseDate, overview, voteAverage, voteCount, genres, images,movieStats
    )


