package co.uk.gkortsaridis.mscodetesttmdb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.uk.gkortsaridis.mscodetesttmdb.R
import co.uk.gkortsaridis.mscodetesttmdb.models.MovieResult
import co.uk.gkortsaridis.mscodetesttmdb.utils.MovieClickListener
import com.squareup.picasso.Picasso


class NowPlayingAdapter(
    private var items: ArrayList<MovieResult> = arrayListOf(),
    private val context: Context,
    private var listener: MovieClickListener? = null
): RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(context).inflate(
            R.layout.movie_card,
            parent,
            false
        )
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var movieTitle: TextView? = null
        private var movieReleaseDate: TextView? = null
        private var movieImg: ImageView? = null
        private var container: LinearLayout? = null

        init {
            movieImg = itemView.findViewById(R.id.movie_img)
            movieTitle = itemView.findViewById(R.id.movie_title)
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date)
            container = itemView.findViewById(R.id.container)
        }

        fun bindItems(item: MovieResult, listener: MovieClickListener?) {
            movieTitle?.text = item.title
            movieReleaseDate?.text = "Released at ${item.releaseDate}"

            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+item.backdropPath).into(movieImg)
            container?.setOnClickListener { listener?.onMovieClicked(item) }
        }
    }

    fun updateItems(items: ArrayList<MovieResult>) {
        this.items = items
        notifyDataSetChanged()
    }
}
