package com.example.myapplication.domain

import android.location.Location

interface LocationRepository {
    suspend fun getUserLocation(): Location
}
