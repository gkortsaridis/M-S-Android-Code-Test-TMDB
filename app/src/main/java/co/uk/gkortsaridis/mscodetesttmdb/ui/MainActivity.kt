package co.uk.gkortsaridis.mscodetesttmdb.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import co.uk.gkortsaridis.mscodetesttmdb.R
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieResult
import co.uk.gkortsaridis.mscodetesttmdb.models.Status
import co.uk.gkortsaridis.mscodetesttmdb.utils.MovieClickListener
import co.uk.gkortsaridis.mscodetesttmdb.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: NowPlayingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //UI Setup
        now_playing_rv.layoutManager = GridLayoutManager(this, 2)
        adapter = NowPlayingAdapter(context = this, listener = this)
        now_playing_rv.adapter = adapter

        //ViewModel & Observing Setup
        mainViewModel.getNowPlayingMovies().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> { updateMovieList(it.data ?: arrayListOf()) }
                Status.LOADING -> { /* Display meaningful loading message*/ }
                Status.ERROR -> { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
            }
        })

    }


    private fun updateMovieList(movies: ArrayList<MovieResult>) {
        adapter.updateItems(movies)
    }

    override fun onMovieClicked(movie: MovieResult) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        startActivity(intent)
    }

}