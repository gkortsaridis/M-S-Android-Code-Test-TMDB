package co.uk.gkortsaridis.mscodetesttmdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieResult
import co.uk.gkortsaridis.mscodetesttmdb.models.Resource
import co.uk.gkortsaridis.mscodetesttmdb.repositories.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val nowPlayingMovies = MutableLiveData<Resource<ArrayList<MovieResult>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        nowPlayingMovies.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getNowPlayingMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        nowPlayingMovies.postValue(Resource.success(data = it.results))
                    }, {
                        nowPlayingMovies.postValue(
                            Resource.error(
                                msg = it.localizedMessage ?: "",
                                data = null
                            )
                        )
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getNowPlayingMovies(): LiveData<Resource<ArrayList<MovieResult>>> {
        return nowPlayingMovies
    }

}