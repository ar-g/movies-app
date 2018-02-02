package arg.movies.features.moviedetails

import arg.movies.fakeMovie
import org.assertj.core.api.Assertions.*
import org.junit.Test

class MovieDetailsViewModelTest{

    val viewModel = MovieDetailsViewModel(fakeMovie)

    @Test
    fun getMovieInfoIsCorrectlyFormatted() {
        assertThat(viewModel.getMovieInfo()).isEqualTo("""

Title:
Olaf's Frozen Adventure

Release date:
2017-10-27

Rating:
6.7

Votes:
86

Adult:
no

Overview:
Olaf is on a mission to harness the best holiday traditions for Anna, Elsa, and Kristoff.""")
    }

    @Test
    fun imagePathIsCorrect() {
        assertThat(viewModel.getImagePath()).isEqualTo("https://image.tmdb.org/t/p/w300/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg")
    }
}