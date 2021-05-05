package co.uk.gkortsaridis.mscodetesttmdb.repositories

import co.uk.gkortsaridis.mscodetesttmdb.models.MovieDetails
import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseMovieCollection
import co.uk.gkortsaridis.mscodetesttmdb.utils.APP_CONSTANTS
import co.uk.gkortsaridis.mscodetesttmdb.utils.ApiClient
import io.reactivex.Observable

class MovieDetailsRepository {

    fun getMovieDetails(movieId: Long): Observable<MovieDetails> {
        return ApiClient.api.getMovieDetails(movieId, APP_CONSTANTS.TMDB_API_KEY)
    }

    fun getCollection(collectionId: Long): Observable<ResponseMovieCollection>{
        return ApiClient.api.getCollection(collectionId, APP_CONSTANTS.TMDB_API_KEY)
    }

}