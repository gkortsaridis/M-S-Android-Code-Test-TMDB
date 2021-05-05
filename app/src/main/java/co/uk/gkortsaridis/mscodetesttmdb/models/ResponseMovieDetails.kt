package co.uk.gkortsaridis.mscodetesttmdb.models

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Long = -1,
    val title: String = "",
    val overview: String = "",

    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("belongs_to_collection")
    val belongsToCollection: MovieCollection? = null,

    //Rest data are not needed for current application.
)

data class MovieCollection (
    val id: Long = -1,
    val name: String = "",
    val poster_path: String = "",
    val backdrop_path: String = ""
)
