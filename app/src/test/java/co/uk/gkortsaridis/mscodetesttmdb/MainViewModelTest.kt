package co.uk.gkortsaridis.mscodetesttmdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieResult
import co.uk.gkortsaridis.mscodetesttmdb.models.Resource
import co.uk.gkortsaridis.mscodetesttmdb.models.ResponseNowPlaying
import co.uk.gkortsaridis.mscodetesttmdb.repositories.MainRepository
import co.uk.gkortsaridis.mscodetesttmdb.viewmodels.MainViewModel
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
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var mainViewModel: MainViewModel

    private val observableNowPlayingMovies = Observable.just(ResponseNowPlaying())
    private val nowPlayingMoviesSuccess = MutableLiveData<Resource<ArrayList<MovieResult>>>()

    @Before
    fun setUp() {
        mainRepository = Mockito.spy(MainRepository())
        mainViewModel = Mockito.spy(MainViewModel(mainRepository))

        nowPlayingMoviesSuccess.postValue(Resource.success(arrayListOf()))

        doReturn(observableNowPlayingMovies).`when`(mainRepository).getNowPlayingMovies()
        doReturn(nowPlayingMoviesSuccess).`when`(mainViewModel).getNowPlayingMovies()
    }

    @Test
    fun repositoryPlayingMoviesSuccess() { assertThat(mainRepository.getNowPlayingMovies()).isEqualTo(observableNowPlayingMovies) }

    @Test
    fun viewModelPlayingMoviesSuccess() { assertThat(mainViewModel.getNowPlayingMovies()).isEqualTo(nowPlayingMoviesSuccess) }

}