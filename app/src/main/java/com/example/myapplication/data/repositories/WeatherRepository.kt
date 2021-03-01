package com.example.myapplication.data.repositories

import com.example.myapplication.data.entities.Weather
import com.example.myapplication.presentation.recyclerview.City

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String) : Weather?
    suspend fun getWeatherById(id: Int): Weather?
    suspend fun getWeatherByGeo(latitude: Double, longitude: Double, count: Int): List<City>
}