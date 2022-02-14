package cainwong.vegan.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import cainwong.vegan.data.Place
import cainwong.vegan.util.rememberMapViewWithLifecycle
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@Composable
fun SearchResultsMap(
    searchState: SearchState?,
    onResultClicked: (Place) -> Unit,
) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(
        map = mapView,
        searchState = searchState,
        onResultClicked = onResultClicked,
    )
}

@Composable
private fun MapViewContainer(
    map: MapView,
    searchState: SearchState?,
    onResultClicked: (Place) -> Unit,
) {
    val zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            val markerMap = HashMap<Marker, Place>()
            val googleMap = mapView.awaitMap()
            googleMap.clear()
            googleMap.uiSettings.isZoomControlsEnabled = true
            var firstPlace: Place? = null
            searchState?.searchResults?.forEachIndexed { i, place ->
                val marker = googleMap.addMarker {
                    position(LatLng(place.lat, place.lng))
                        .title(place.name)
                }?.also { markerMap[it] = place }
                if (i == 0) {
                    firstPlace = place
                    marker?.showInfoWindow()
                }
            }
            var latLng = searchState?.location?.let { LatLng(it.lat, it.lng) }
            firstPlace?.also { latLng = LatLng(it.lat, it.lng) }
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(zoom))
            latLng?.also { googleMap.moveCamera(CameraUpdateFactory.newLatLng(it)) }
        }
    }
}

private const val InitialZoom = 15f
