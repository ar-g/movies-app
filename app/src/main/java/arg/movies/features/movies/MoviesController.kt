package arg.movies.features.movies

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import arg.movies.R
import arg.movies.data.api.Api
import arg.movies.data.api.model.Movie
import arg.movies.features.moviedetails.MovieDetailsController
import arg.movies.features.movies.MoviesViewState.*
import arg.movies.mvp.MvpController
import arg.movies.mvp.MvpView
import arg.movies.pagination.RvPager2
import com.bluelinelabs.conductor.RouterTransaction
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.controller_movies.view.*
import kotlin.properties.Delegates.observable

class MoviesController : MvpController<MoviesPresenter, MoviesView>(), MoviesView {

    private lateinit var adapter: MoviesAdapter
    private var paginationDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_movies, container, false)

        view.rvMovies.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.rvMovies.setHasFixedSize(true)

        adapter = MoviesAdapter {
            presenter.onMovieClick(it)
        }
        adapter.setHasStableIds(true)
        view.rvMovies.adapter = adapter

        setPagination(view)

        view.swipeLayout.setOnRefreshListener {
            presenter.loadFirstPage()
        }

        return view
    }

    private fun setPagination(view: View) {
        paginationDisposable = RvPager2(view.rvMovies)
                .pageEvents()
                .subscribe(presenter::loadPage)
    }

    private fun resetPagination() {
        view?.let { view ->
            paginationDisposable?.dispose()
            setPagination(view)
        }
    }

    override fun navigateToMovie(movie: Movie) {
        router.pushController(RouterTransaction.with(MovieDetailsController(movie)))
    }

    override fun providePresenter(): MoviesPresenter {
        return MoviesPresenter(MovieOperations(Api()))
    }

    override var viewState: MoviesViewState by observable<MoviesViewState>(
            Init(),
            { _, _, newState ->
                when (newState) {
                    is MoviesViewState.Init -> { /*NOP*/ }
                    is MoviesViewState.Loading -> {
                        view?.swipeLayout?.isRefreshing = true
                    }
                    is MoviesViewState.SuccessFirstPage -> {
                        resetPagination()
                        view?.swipeLayout?.isRefreshing = false
                        adapter.setData(newState.items)
                    }
                    is MoviesViewState.SuccessAppend-> {
                        view?.swipeLayout?.isRefreshing = false
                        adapter.appendData(newState.items)
                    }
                    is MoviesViewState.Error -> {
                        view?.swipeLayout?.isRefreshing = false
                        Toast.makeText(activity, newState.errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            })
}

interface MoviesView: MvpView {
    var viewState: MoviesViewState

    fun navigateToMovie(movie: Movie)
}

sealed class MoviesViewState {
    class Init : MoviesViewState()
    class Loading : MoviesViewState()
    class SuccessFirstPage(val items: List<MovieViewModel>) : MoviesViewState()
    class SuccessAppend(val items: List<MovieViewModel>) : MoviesViewState()
    class Error(val errorMessage: String?) : MoviesViewState()
}