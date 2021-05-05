package co.uk.gkortsaridis.mscodetesttmdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.gkortsaridis.mscodetesttmdb.R
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieDetails
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieResult
import co.uk.gkortsaridis.mscodetesttmdb.models.Status
import co.uk.gkortsaridis.mscodetesttmdb.utils.MovieClickListener
import co.uk.gkortsaridis.mscodetesttmdb.viewmodels.MovieDetailsViewModel
import co.uk.gkortsaridis.mscodetesttmdb.viewmodels.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieClickListener {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var adapter: MovieCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieId = intent.getLongExtra("MOVIE_ID", -1)

        adapter = MovieCollectionAdapter(context = this, listener = this)
        collection_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        collection_rv.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelFactory()).get(MovieDetailsViewModel::class.java)
        viewModel.getMovieDetails(movieId).observe(this, {
            when(it.status) {
                Status.LOADING -> { /* Display meaningful loading message*/ }
                Status.SUCCESS ->{ if(it.data != null) { displayMovieDetails(it.data) } }
                Status.ERROR -> { Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show() }
            }
        })
    }

    private fun displayMovieDetails(movie: MovieDetails) {
        movie_title.text = movie.title
        movie_description.text = movie.overview
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.backdropPath).into(movie_img)

        if(movie.belongsToCollection != null) {
            collection_container.visibility = View.VISIBLE

            viewModel.getMovieCollection(movie.belongsToCollection.id).observe(this, {
                when(it.status) {
                    Status.LOADING -> { /* Display meaningful loading message*/ }
                    Status.SUCCESS ->{ if(it.data != null) { updateCollection(it.data.parts) } }
                    Status.ERROR -> { Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show() }
                }
            })

        } else {
            collection_container.visibility = View.GONE
        }

        collection_rv.visibility = if(movie.belongsToCollection != null) View.VISIBLE else View.GONE
    }

    private fun updateCollection(movies: ArrayList<MovieResult>) {
        adapter.updateItems(movies)
    }

    override fun onMovieClicked(movie: MovieResult) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        startActivity(intent)
    }
}