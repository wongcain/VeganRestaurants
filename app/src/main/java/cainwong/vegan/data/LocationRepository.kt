package cainwong.vegan.data

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import cainwong.vegan.util.Resource
import cainwong.vegan.util.logE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface LocationRepository {
    val location: Flow<Resource<Location>>
}

class LocationRepositoryImpl @Inject constructor(
    app: Application,
    locationProvider: FusedLocationProviderClient,
) : LocationRepository, OnCompleteListener<android.location.Location> {

    private val _location = MutableStateFlow<Resource<Location>>(Resource.Loading())

    override val location: Flow<Resource<Location>> = _location

    init {
        if (ActivityCompat.checkSelfPermission(
                app,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationProvider.getCurrentLocation(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).addOnCompleteListener(this)
        }
    }

    // TODO: Unit Test
    override fun onComplete(loc: Task<android.location.Location>) {
        _location.value = if (loc.isSuccessful) {
            Resource.Success(Location(loc.result.latitude, loc.result.longitude))
        } else {
            logE(loc.exception) { "Error getting location." }
            Resource.Error(loc.exception)
        }
    }
}
