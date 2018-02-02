package arg.movies.features.moviedetails

import arg.movies.data.api.model.Movie
import arg.movies.features.movies.MovieViewModel

class MovieDetailsViewModel(movie: Movie): MovieViewModel(movie) {
    fun getMovieInfo(): String {
        return """
            |
            |
            |Title:
            |${movie.title}
            |
            |Release date:
            |${movie.releaseDate}
            |
            |Rating:
            |${movie.voteAverage}
            |
            |Votes:
            |${movie.voteCount}
            |
            |Adult:
            |${if (movie.adult) "yes" else "no"}
            |
            |Overview:
            |${movie.overview}""".trimMargin()
    }
}