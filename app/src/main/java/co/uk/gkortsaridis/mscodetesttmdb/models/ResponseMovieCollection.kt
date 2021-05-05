package co.uk.gkortsaridis.mscodetesttmdb.models

data class ResponseMovieCollection(
    val id: Long,
    val name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val parts: ArrayList<MovieResult>
)