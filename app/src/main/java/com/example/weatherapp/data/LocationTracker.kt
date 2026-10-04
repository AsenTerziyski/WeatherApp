package com.example.weatherapp.data

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTracker @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient) {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                continuation.resume(location)
            }
                .addOnFailureListener { e ->
                    continuation.resume(null)
                }
        }
}