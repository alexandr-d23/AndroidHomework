package com.example.myapplication.domain

import com.example.myapplication.data.repositories.WeatherRepository
import com.example.myapplication.data.entities.Weather
import com.example.myapplication.presentation.recyclerview.City
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FindCityUseCase
(
        private val weatherRepository: WeatherRepository,
        private val context: CoroutineContext
) {

    suspend fun getWeatherByCityName(cityName: String): Weather? =
            withContext(context){
                weatherRepository.getWeatherByCityName(cityName)
            }

    suspend fun getWeatherById(id: Int): Weather? =
            withContext(context){
                weatherRepository.getWeatherById(id)
            }

    suspend fun getWeatherByGeo(latitude: Double, longitude: Double, count: Int): List<City> =
            withContext(context){
                weatherRepository.getWeatherByGeo(latitude, longitude, count)
            }
}