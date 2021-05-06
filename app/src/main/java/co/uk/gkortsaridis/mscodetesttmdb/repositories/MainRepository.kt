package co.uk.gkortsaridis.mscodetesttmdb.repositories

import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseNowPlaying
import co.uk.gkortsaridis.mscodetesttmdb.utils.APP_CONSTANTS
import co.uk.gkortsaridis.mscodetesttmdb.utils.ApiClient
import io.reactivex.Observable
import javax.inject.Inject


class MainRepository @Inject constructor() {

    fun getNowPlayingMovies(): Observable<ResponseNowPlaying> {
        return ApiClient.api.getNowPlaying(APP_CONSTANTS.TMDB_API_KEY)
    }

}