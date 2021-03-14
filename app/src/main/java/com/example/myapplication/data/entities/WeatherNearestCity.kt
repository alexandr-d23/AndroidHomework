package com.example.myapplication.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.presentation.recyclerview.City

@Entity(tableName = "city")
data class WeatherNearestCity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @Embedded(prefix = "city_")
    var city: City
)
