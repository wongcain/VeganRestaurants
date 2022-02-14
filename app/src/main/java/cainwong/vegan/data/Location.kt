package cainwong.vegan.data

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("Lat")
    val lat: Double,
    @SerializedName("Lng")
    val lng: Double,
) {
    override fun toString() = "$lat,$lng"
}
