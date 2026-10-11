package com.example.weatherapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTracker @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocationData(): LocationData? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    // Fetch city name asynchronously in IO dispatcher
                    kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
                        val cityName = getCityName(location.latitude, location.longitude) ?: "My Current Location"
                        continuation.resume(LocationData(location.latitude, location.longitude, cityName))
                    }
                } else {
                    continuation.resume(null)
                }
            }.addOnFailureListener {
                continuation.resume(null)
            }
        }

    private suspend fun getCityName(
        lat: Double,
        lon: Double
    ): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(lat, lon, 1, object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            val city = addresses.firstOrNull()?.locality
                                ?: addresses.firstOrNull()?.subAdminArea
                                ?: addresses.firstOrNull()?.adminArea
                            continuation.resume(city)
                        }

                        override fun onError(errorMessage: String?) {
                            continuation.resume(null)
                        }
                    })
                }
            } else {
                withContext(Dispatchers.IO) {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(lat, lon, 1)
                    addresses?.firstOrNull()?.let { address ->
                        address.locality ?: address.subAdminArea ?: address.adminArea
                    }
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val cityName: String
)
