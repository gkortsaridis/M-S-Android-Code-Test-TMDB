package co.uk.gkortsaridis.mscodetesttmdb.utils

import co.uk.gkortsaridis.mscodetesttmdb.models.MovieDetails
import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseMovieCollection
import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseNowPlaying
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_API_URL = "https://api.themoviedb.org/3/"
    private const val NETWORK_TIMEOUT_SECONDS = 30L

    private val customApiClient = OkHttpClient.Builder()
        .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(customApiClient)
        .baseUrl(BASE_API_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val api = retrofit.create(CustomApiInterface::class.java)

    interface CustomApiInterface {
        @GET("movie/now_playing")
        fun getNowPlaying(@Query("api_key") apiKey: String): Observable<ResponseNowPlaying>

        @GET("movie/{movieId}")
        fun getMovieDetails(@Path("movieId") movieId: Long, @Query("api_key") apiKey: String): Observable<MovieDetails>

        @GET("collection/{collectionId}")
        fun getCollection(@Path("collectionId") collectionId: Long, @Query("api_key") apiKey: String): Observable<ResponseMovieCollection>
    }

}