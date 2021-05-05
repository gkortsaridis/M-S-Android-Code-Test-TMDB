package co.uk.gkortsaridis.mscodetesttmdb.models

import com.google.gson.annotations.SerializedName

data class ResponseNowPlaying (
    val page: Int = 0,
    val results: ArrayList<MovieResult> = arrayListOf(),

    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)