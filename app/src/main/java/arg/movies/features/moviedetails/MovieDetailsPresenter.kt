package arg.movies.features.moviedetails

import arg.movies.data.api.model.Movie
import arg.movies.mvp.Presenter

class MovieDetailsPresenter(private val movie: Movie) : Presenter<MovieDetailsView>() {
    override fun onAttachView(view: MovieDetailsView) {
        super.onAttachView(view)
        view.viewState = MovieDetailsViewState.Start(MovieDetailsViewModel(movie))
    }
}