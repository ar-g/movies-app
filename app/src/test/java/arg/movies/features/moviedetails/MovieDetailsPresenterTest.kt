package arg.movies.features.moviedetails

import arg.movies.fakeMovie
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.isA
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test

class MovieDetailsPresenterTest{
    lateinit var view : MovieDetailsView
    lateinit var presenter : MovieDetailsPresenter

    @Before
    fun setUp() {
        view = mock()
        presenter = MovieDetailsPresenter(fakeMovie)
    }

    @Test
    fun onAttachSetsProperState() {
        presenter.onAttachView(view)

        verify(view).viewState = isA<MovieDetailsViewState.Start>()

        argumentCaptor<MovieDetailsViewState.Start>().apply{
            verify(view).viewState = capture()
            assertThat(firstValue.viewModel.movie).isEqualTo(fakeMovie)
        }
    }
}