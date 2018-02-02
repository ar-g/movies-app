package arg.movies.features.movies

import arg.movies.OverrideRxJavaSchedulersRule
import arg.movies.data.api.ApiType
import arg.movies.data.api.model.Movies
import arg.movies.fakeErrorMsg
import arg.movies.fakeMovie
import arg.movies.fakeMovies
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesPresenterTest{
    @Rule
    @JvmField val overrideSchedulersRule = OverrideRxJavaSchedulersRule()

    lateinit var view : MoviesView
    lateinit var presenter : MoviesPresenter

    @Before
    fun setUp() {
        view = mock()
        presenter = MoviesPresenter(MovieOperations(
                object : ApiType {
                    override fun getPopularMovies(page: Int): Observable<Movies> {
                        return Observable.just(fakeMovies)
                    }
                }
        ))
    }

    @Test
    fun onAttachLoadsFirstPageAndSetsProperStates() {
        presenter.onAttachView(view)

        verify(view).viewState = isA<MoviesViewState.Loading>()
        verify(view).viewState = isA<MoviesViewState.SuccessFirstPage>()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loadPageLoadsProperData(){
        presenter.onAttachView(view)

        argumentCaptor<MoviesViewState.SuccessFirstPage>().apply {
            verify(view, times(2)).viewState = capture()

            assertThat(secondValue.items[0].movie).isEqualTo(fakeMovie)
        }
    }

    @Test
    fun loadSecondPageSetsCorrectState(){
        presenter.onAttachView(view)

        presenter.loadPage(2)

        verify(view, times(2)).viewState = isA<MoviesViewState.Loading>()
        verify(view).viewState = isA<MoviesViewState.SuccessFirstPage>()
        verify(view).viewState = isA<MoviesViewState.SuccessAppend>()

        verifyNoMoreInteractions(view)
    }


    @Test
    fun screenRotationHandledProperly(){
        presenter.onAttachView(view)

        presenter.loadPage(2)
        presenter.loadPage(3)

        presenter.onAttachView(view)

        argumentCaptor<MoviesViewState.SuccessFirstPage>().apply {
            verify(view, atLeastOnce()).viewState = capture()

            assertThat(lastValue.items.size).isEqualTo(3)
        }
    }

    @Test
    fun errorSetsCorrectState(){
        val presenter = MoviesPresenter(MovieOperations(object : ApiType {
            override fun getPopularMovies(page: Int): Observable<Movies> {
                return Observable.error(RuntimeException(fakeErrorMsg))
            }
        }))
        presenter.onAttachView(view)

        verify(view).viewState = isA<MoviesViewState.Loading>()
        verify(view).viewState = isA<MoviesViewState.Error>()

        argumentCaptor<MoviesViewState.Error>().apply {
            verify(view, atLeastOnce()).viewState = capture()

            assertThat(secondValue.errorMessage).isEqualTo(fakeErrorMsg)
        }

        verifyNoMoreInteractions(view)
    }
}