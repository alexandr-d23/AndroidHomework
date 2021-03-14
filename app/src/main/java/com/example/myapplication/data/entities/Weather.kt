package com.example.myapplication.data.entities;

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey
    var id: Int,
    @Embedded(prefix = "main_")
    var main: WeatherResponse.Main,
    var name: String,
    @Embedded(prefix = "weather_")
    var innerWeather: WeatherResponse.InnerWeather,
    @Embedded(prefix = "wind_")
    var wind: WeatherResponse.Wind
)
