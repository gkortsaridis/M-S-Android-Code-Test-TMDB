package co.uk.gkortsaridis.mscodetesttmdb.repositories

import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseNowPlaying
import co.uk.gkortsaridis.mscodetesttmdb.utils.APP_CONSTANTS
import co.uk.gkortsaridis.mscodetesttmdb.utils.ApiClient
import io.reactivex.Observable

class MainRepository() {

    fun getNowPlayingMovies(): Observable<ResponseNowPlaying> {
        return ApiClient.api.getNowPlaying(APP_CONSTANTS.TMDB_API_KEY)
    }

}