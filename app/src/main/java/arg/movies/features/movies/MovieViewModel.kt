package arg.movies.features.movies

import arg.movies.data.api.IMAGE_PATH
import arg.movies.data.api.model.Movie

open class MovieViewModel(val movie: Movie){
    fun getImagePath(): String {
        return IMAGE_PATH + movie.posterPath
    }
}