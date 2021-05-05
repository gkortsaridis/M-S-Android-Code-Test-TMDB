package co.uk.gkortsaridis.mscodetesttmdb.models

import com.google.gson.annotations.SerializedName

data class MovieResult(
    val id: Long,
    val adult: Boolean,
    val overview: String,
    val popularity: Double,
    val title: String,
    val video: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIDS: List<Long>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long
)