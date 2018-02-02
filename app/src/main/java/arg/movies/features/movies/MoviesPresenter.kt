package arg.movies.features.movies

import arg.movies.mvp.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MoviesPresenter(
        private val movieOperations: MovieOperations
) : Presenter<MoviesView>() {

    private var popularMoviesDisposable: Disposable? = null
    private var movieViewModels: MutableList<MovieViewModel> = mutableListOf()

    override fun onAttachView(view: MoviesView) {
        super.onAttachView(view)
        if (!movieViewModels.isEmpty()) {
            view.viewState = MoviesViewState.SuccessFirstPage(movieViewModels)
        } else {
            loadPage(1)
        }
    }

    fun loadFirstPage() {
        loadPage(FIRST_PAGE)
    }

    fun loadPage(page: Int) {
        view?.let { view ->
            popularMoviesDisposable = movieOperations.getPopularMovies(page)
                    .map { movies -> movies.map(::MovieViewModel) }
                    .map { movieViewModels ->
                        if (page == FIRST_PAGE) {
                            this.movieViewModels.clear()
                        }
                        this.movieViewModels.addAll(movieViewModels)
                        return@map if (page == FIRST_PAGE) {
                            MoviesViewState.SuccessFirstPage(this.movieViewModels)
                        } else {
                            MoviesViewState.SuccessAppend(this.movieViewModels)
                        }
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn { e -> MoviesViewState.Error(e.message) }
                    .startWith(MoviesViewState.Loading())
                    .subscribe { viewState -> view.viewState = viewState }
        }
    }

    fun onMovieClick(viewModel: MovieViewModel) {
        view?.navigateToMovie(viewModel.movie)
    }

    override fun onDetachView() {
        super.onDetachView()
        popularMoviesDisposable?.dispose()
    }

    companion object {
        val FIRST_PAGE = 1
    }
}