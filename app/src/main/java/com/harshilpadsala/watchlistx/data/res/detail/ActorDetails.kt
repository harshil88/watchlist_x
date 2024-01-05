package com.harshilpadsala.watchlistx.data.res.detail

import com.google.gson.annotations.SerializedName
import com.harshilpadsala.watchlistx.Constant.TMDB_IMAGE_URI_HIGH
import com.harshilpadsala.watchlistx.data.res.model.CardModel

data class ActorDetails(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("cast_id")
    val castId: Int?,
    val character: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    val order: Int?
) {
    companion object {
        fun toCardComponent() {
            TODO("Not yet implemented")
        }
    }
}



fun ActorDetails.toCardComponent() : CardModel = CardModel(
    id = this.id,
    title = this.name,
    imageUri = TMDB_IMAGE_URI_HIGH +  this.profilePath,
)

