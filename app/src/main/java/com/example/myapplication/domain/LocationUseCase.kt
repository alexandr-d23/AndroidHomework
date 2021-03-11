package com.example.myapplication.domain

import android.location.Location
import com.example.myapplication.data.repositories.LocationRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val context: CoroutineContext
) {
    suspend fun getUserLocation(): Location =
        withContext(context) {
            locationRepository.getUserLocation()
        }
}