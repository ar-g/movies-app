package arg.movies.features.movies

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import arg.movies.R

class MoviesAdapter(
        private val onItemClick: (MovieViewModel) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {

    private val data: MutableList<MovieViewModel> = mutableListOf()

    fun appendData(movies: List<MovieViewModel>) {
        val position = data.size
        data.addAll(movies)
        notifyItemRangeInserted(position, movies.size)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = data[position]
        holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(movies: List<MovieViewModel>) {
        data.clear()
        data.addAll(movies)
        notifyDataSetChanged()
    }
}