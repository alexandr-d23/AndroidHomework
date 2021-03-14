package com.example.myapplication.domain

import android.location.Location
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class LocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    @Named("ioCoroutineContext")
    private val context: CoroutineContext
) {
    suspend fun getUserLocation(): Location =
        withContext(context) {
            locationRepository.getUserLocation()
        }
}
