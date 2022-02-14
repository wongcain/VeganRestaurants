package cainwong.vegan.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import cainwong.vegan.api.PlacesApi
import cainwong.vegan.api.PlacesApiResult

@Entity(tableName = "Place")
data class Place(
    @PrimaryKey
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val photoRef: String?,
    val priceLevel: Int,
    val rating: Float,
    val userRatingsTotal: Int,
    val address: String,
) {
    fun photoUrl(
        maxWidth: Int,
    ) = photoRef?.let { PlacesApi.photoUrl(maxWidth = maxWidth, photoReference = it) }

    fun directionsUrl() = photoRef?.let { PlacesApi.directionsUrl(destination = address) }
}

fun PlacesApiResult.toPlace() = Place(
    id = id,
    name = name,
    lat = geometry.location.lat,
    lng = geometry.location.lng,
    photoRef = photos?.firstOrNull()?.photoReference,
    priceLevel = priceLevel,
    rating = rating,
    userRatingsTotal = userRatingsTotal,
    address = address,
)
