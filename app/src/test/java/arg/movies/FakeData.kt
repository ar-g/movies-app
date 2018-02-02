package arg.movies



import arg.movies.data.api.model.Movie
import arg.movies.data.api.model.Movies

val fakeMovie = Movie(460793, 86, 6.7, "Olaf's Frozen Adventure", 150.660279, "47pLZ1gr63WaciDfHCpmoiXJlVr.jpg", false, "Olaf is on a mission to harness the best holiday traditions for Anna, Elsa, and Kristoff.", "2017-10-27")
val fakeMovies = Movies(2, 19842, 993, listOf(fakeMovie))
val fakeErrorMsg = "error"