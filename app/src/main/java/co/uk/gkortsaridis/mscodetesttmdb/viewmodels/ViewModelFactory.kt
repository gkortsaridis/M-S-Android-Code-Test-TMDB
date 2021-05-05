package co.uk.gkortsaridis.mscodetesttmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.uk.gkortsaridis.mscodetesttmdb.repositories.MainRepository
import co.uk.gkortsaridis.mscodetesttmdb.repositories.MovieDetailsRepository

class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val mainRepository = MainRepository()
            return MainViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            val movieDetailsRepository = MovieDetailsRepository()
            return MovieDetailsViewModel(movieDetailsRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}