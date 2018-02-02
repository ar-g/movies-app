package arg.movies.data.api

import arg.movies.data.api.model.Movies
import com.google.gson.Gson
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val IMAGE_PATH = "https://image.tmdb.org/t/p/w300/"
const val BASE_URL = "https://api.themoviedb.org/"
const val API_KEY = "6b095225d7fd41d752bb7bcac02e71dd"

class Api: ApiType {
    private val api: ApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create<ApiService>(ApiService::class.java)


    override fun getPopularMovies(page: Int): Observable<Movies> {
        return api.getPopularMovies(API_KEY, page)
    }
}

interface ApiType {
    fun getPopularMovies(page: Int) : Observable<Movies>
}

interface ApiService {
    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int) : Observable<Movies>
}