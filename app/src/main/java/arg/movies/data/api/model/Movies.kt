package arg.movies.data.api.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize data class Movies(
		@SerializedName("page") val page: Int, //2
		@SerializedName("total_results") val totalResults: Int, //19842
		@SerializedName("total_pages") val totalPages: Int, //993
		@SerializedName("results") val results: List<Movie>
) : Parcelable

