package arg.movies.features.movies

import android.support.v7.widget.RecyclerView
import android.view.View
import arg.movies.imageloading.load
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieViewHolder(itemView: View, onItemClick: (MovieViewModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private var viewModel: MovieViewModel? = null

    init {
        itemView.ivMovie.setOnClickListener {
            viewModel?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(viewModel: MovieViewModel) {
        this.viewModel = viewModel
        itemView.ivMovie.load(viewModel.getImagePath())
    }
}