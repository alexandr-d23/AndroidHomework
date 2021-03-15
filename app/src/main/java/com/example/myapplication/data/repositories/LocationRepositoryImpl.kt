package com.example.myapplication.data.repositories

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import com.example.myapplication.domain.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl @Inject constructor(
    private val client: FusedLocationProviderClient
) : LocationRepository {

    @RequiresPermission(
        allOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    override suspend fun getUserLocation(): Location = suspendCancellableCoroutine { continuation ->
        client.lastLocation.addOnSuccessListener {
                continuation.resume(it)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
}
