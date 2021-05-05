package co.uk.gkortsaridis.mscodetesttmdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieCollection
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieDetails
import co.uk.gkortsaridis.mscodetesttmdb.models.Resource
import co.uk.gkortsaridis.mscodetesttmdb.repositories.MovieDetailsRepository
import co.uk.gkortsaridis.mscodetesttmdb.viewmodels.MovieDetailsViewModel
import com.google.common.truth.Truth.assertThat
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    private val CORRECT_MOVIE_ID = 1L
    private val INCORRECT_MOVIE_ID = -1L
    private val CORRECT_COLLECTION_ID = 2L
    private val INCORRECT_COLLECTION_ID = -2L

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    @Mock
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private val movieDetailsObservable = Observable.just(MovieDetails())
    private val movieDetailsSuccess = MutableLiveData<Resource<MovieDetails>>()
    private val movieDetailsError = MutableLiveData<Resource<MovieDetails>>()
    private val customError = RuntimeException("Custom Error msg")

    private val collectionDetailsObservable = Observable.just(MovieCollection())
    private val collectionDetailsSuccess = MutableLiveData<Resource<MovieCollection>>()
    private val collectionDetailsError = MutableLiveData<Resource<MovieDetails>>()

    @Before
    fun setUp() {
        movieDetailsRepository = Mockito.spy(MovieDetailsRepository())
        movieDetailsViewModel = Mockito.spy(MovieDetailsViewModel(movieDetailsRepository))

        movieDetailsSuccess.postValue(Resource.success(MovieDetails()))
        movieDetailsError.postValue(Resource.error(msg = "Custom Error msg", data = null))
        collectionDetailsSuccess.postValue(Resource.success(MovieCollection()))
        collectionDetailsError.postValue(Resource.error("Custom Error msg", data = null))

        //Setup Mock responses for correct and incorrect calls
        Mockito.doReturn(movieDetailsObservable).`when`(movieDetailsRepository).getMovieDetails(CORRECT_MOVIE_ID)
        Mockito.doReturn(movieDetailsSuccess).`when`(movieDetailsViewModel).getMovieDetails(CORRECT_MOVIE_ID)

        Mockito.doReturn(movieDetailsError).`when`(movieDetailsViewModel).getMovieDetails(INCORRECT_MOVIE_ID)
        Mockito.doThrow(customError).`when`(movieDetailsRepository).getMovieDetails(INCORRECT_MOVIE_ID)

        Mockito.doReturn(collectionDetailsObservable).`when`(movieDetailsRepository).getCollection(CORRECT_COLLECTION_ID)
        Mockito.doReturn(collectionDetailsSuccess).`when`(movieDetailsViewModel).getMovieCollection(CORRECT_COLLECTION_ID)

        Mockito.doReturn(collectionDetailsError).`when`(movieDetailsViewModel).getMovieCollection(INCORRECT_COLLECTION_ID)
        Mockito.doThrow(customError).`when`(movieDetailsRepository).getCollection(INCORRECT_COLLECTION_ID)
    }

    //MOVIE DETAILS
    @Test
    fun repositoryMovieDetailsSuccess() { assertThat(movieDetailsRepository.getMovieDetails(CORRECT_MOVIE_ID)).isEqualTo(movieDetailsObservable) }

    @Test
    fun viewModelMovieDetailsSuccess() { assertThat(movieDetailsViewModel.getMovieDetails(CORRECT_MOVIE_ID)).isEqualTo(movieDetailsSuccess) }

    @Test(expected= Exception::class)
    fun repositoryMovieDetailsError() { movieDetailsRepository.getMovieDetails(INCORRECT_MOVIE_ID) }

    @Test
    fun viewModelMovieDetailsError() { assertThat(movieDetailsViewModel.getMovieDetails(INCORRECT_MOVIE_ID)).isEqualTo(movieDetailsError) }

    //COLLECTIONS
    @Test
    fun repositoryCollectionDetailsSuccess() { assertThat(movieDetailsRepository.getCollection(CORRECT_COLLECTION_ID)).isEqualTo(collectionDetailsObservable) }

    @Test
    fun viewModelCollectionDetailsSuccess() { assertThat(movieDetailsViewModel.getMovieCollection(CORRECT_COLLECTION_ID)).isEqualTo(collectionDetailsSuccess) }

    @Test(expected= Exception::class)
    fun repositoryCollectionDetailsError() { movieDetailsRepository.getCollection(INCORRECT_COLLECTION_ID) }

    @Test
    fun viewModelCollectionDetailsError() { assertThat(movieDetailsViewModel.getMovieCollection(INCORRECT_COLLECTION_ID)).isEqualTo(collectionDetailsError) }

}