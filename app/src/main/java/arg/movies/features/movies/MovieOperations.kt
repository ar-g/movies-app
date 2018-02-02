package arg.movies.features.movies

import arg.movies.data.api.ApiType
import arg.movies.data.api.model.Movie
import io.reactivex.Observable

class MovieOperations(
        private val api: ApiType
){
    fun getPopularMovies(page: Int): Observable<List<Movie>> {
        return api.getPopularMovies(page)
                .map { it.results }
    }
}