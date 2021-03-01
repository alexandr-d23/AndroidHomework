package com.example.myapplication.data.repositories

import android.location.Location

interface LocationRepository {
    suspend fun getUserLocation(): Location
}