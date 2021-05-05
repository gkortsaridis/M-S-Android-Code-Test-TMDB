package co.uk.gkortsaridis.mscodetesttmdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieDetails
import co.uk.gkortsaridis.mscodetesttmdb.models.Resource
import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseMovieCollection
import co.uk.gkortsaridis.mscodetesttmdb.repositories.MovieDetailsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsViewModel(private val movieDetailsRepository: MovieDetailsRepository): ViewModel() {

    private val movieDetails = MutableLiveData<Resource<MovieDetails>>()
    private val movieCollection = MutableLiveData<Resource<ResponseMovieCollection>>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getMovieDetails(movieId: Long): LiveData<Resource<MovieDetails>> {
        movieDetails.postValue(Resource.loading(null))
        try {
            compositeDisposable.add(
                movieDetailsRepository.getMovieDetails(movieId = movieId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { movieDetails.postValue(Resource.success(data = it)) },
                        { movieDetails.postValue(Resource.error(msg = it.localizedMessage, data = null)) }
                    )
            )
        } catch(e: Exception) {
            movieDetails.postValue(Resource.error(msg = e.localizedMessage, data = null))
        }

        return movieDetails
    }

    fun getMovieCollection(collectionId: Long): LiveData<Resource<ResponseMovieCollection>> {
        movieCollection.postValue(Resource.loading(null))
        try {
            compositeDisposable.add(
                movieDetailsRepository.getCollection(collectionId = collectionId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { movieCollection.postValue(Resource.success(data = it)) },
                        { movieCollection.postValue(Resource.error(msg = it.localizedMessage, data = null)) }
                    )
            )
        } catch(e: Exception) {
            movieCollection.postValue(Resource.error(msg = e.localizedMessage, data = null))
        }

        return movieCollection
    }

}