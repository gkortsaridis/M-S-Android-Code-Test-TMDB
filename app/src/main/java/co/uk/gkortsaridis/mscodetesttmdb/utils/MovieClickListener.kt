package co.uk.gkortsaridis.mscodetesttmdb.utils

import co.uk.gkortsaridis.mscodetesttmdb.models.MovieResult

interface MovieClickListener {
    fun onMovieClicked(movie: MovieResult)
}