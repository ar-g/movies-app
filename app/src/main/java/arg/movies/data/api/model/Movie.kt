package arg.movies.data.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize data class Movie(
        @SerializedName("id") val id: Int, //460793
        @SerializedName("vote_count") val voteCount: Int, //86
        @SerializedName("vote_average") val voteAverage: Double, //6.7
        @SerializedName("title") val title: String, //Olaf's Frozen Adventure
        @SerializedName("popularity") val popularity: Double, //150.660279
        @SerializedName("poster_path") val posterPath: String?, ///47pLZ1gr63WaciDfHCpmoiXJlVr.jpg
        @SerializedName("adult") val adult: Boolean, //false
        @SerializedName("overview") val overview: String, //Olaf is on a mission to harness the best holiday traditions for Anna, Elsa, and Kristoff.
        @SerializedName("release_date") val releaseDate: String //2017-10-27
) : Parcelable