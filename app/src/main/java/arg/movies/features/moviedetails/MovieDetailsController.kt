package arg.movies.features.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import arg.movies.R
import arg.movies.data.api.model.Movie
import arg.movies.features.moviedetails.MovieDetailsViewState.*
import arg.movies.imageloading.load
import arg.movies.mvp.MvpController
import arg.movies.mvp.MvpView
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.controller_movie_details.view.*

class MovieDetailsController(args: Bundle) : MvpController<MovieDetailsPresenter, MovieDetailsView>(args), MovieDetailsView {

    constructor(movie: Movie) : this(
            Bundle().apply { putParcelable(Movie::class.java.name, movie) }
    )

    override fun providePresenter(): MovieDetailsPresenter {
        return MovieDetailsPresenter(args.getParcelable(Movie::class.java.name))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_movie_details, container, false)
        return view
    }

    override var viewState: MovieDetailsViewState by Delegates.observable<MovieDetailsViewState>(Init(),
            { _, _, newState ->
                when(newState){
                    is MovieDetailsViewState.Init -> {/*NOP*/}
                    is MovieDetailsViewState.Start -> {
                        view?.tvMovieInfo?.text = newState.viewModel.getMovieInfo()
                        view?.ivMovie?.load(newState.viewModel.getImagePath())
                    }
                }
            }
    )
}

interface MovieDetailsView : MvpView {
    var viewState: MovieDetailsViewState
}

sealed class MovieDetailsViewState {
    class Init : MovieDetailsViewState()
    class Start(val viewModel: MovieDetailsViewModel) : MovieDetailsViewState()
}

