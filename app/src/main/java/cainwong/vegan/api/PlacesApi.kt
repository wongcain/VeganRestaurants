package cainwong.vegan.api

import cainwong.vegan.BuildConfig
import cainwong.vegan.data.Location
import cainwong.vegan.data.PlaceType
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {
    companion object {
        val PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/"
        val MAPS_DIRECTIONS_URL = "https://www.google.com/maps/dir/?api=1"
        private const val API_KEY = BuildConfig.apiKey
        fun photoUrl(
            maxWidth: Int,
            photoReference: String,
        ) =
            "${PLACES_BASE_URL}photo?photo_reference=$photoReference&maxwidth=$maxWidth&key=$API_KEY"

        fun directionsUrl(
            destination: String,
        ) = "$MAPS_DIRECTIONS_URL&destination=$destination"
    }

    @GET("textsearch/json")
    suspend fun getPlaces(
        @Query("location") location: Location,
        @Query("type") type: PlaceType? = null,
        @Query("query") query: String? = null,
        @Query("key") key: String = API_KEY,
    ): PlacesApiResponse
}

data class PlacesApiResponse(
    val results: List<PlacesApiResult>
)

data class PlacesApiResult(
    @SerializedName("place_id")
    val id: String,
    val name: String,
    val geometry: PlacesApiGeometry,
    val photos: List<Photo>?,
    @SerializedName("price_level")
    val priceLevel: Int,
    val rating: Float,
    @SerializedName("user_ratings_total")
    val userRatingsTotal: Int,
    @SerializedName("formatted_address")
    val address: String,
)

data class PlacesApiGeometry(
    @SerializedName("location")
    val location: PlacesApiLocation,
)

data class PlacesApiLocation(
    val lat: Double,
    val lng: Double,
)

data class Photo(
    @SerializedName("photo_reference")
    val photoReference: String
)
