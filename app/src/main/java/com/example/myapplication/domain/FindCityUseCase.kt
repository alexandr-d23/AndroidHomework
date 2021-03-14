package com.example.myapplication.domain

import com.example.myapplication.data.entities.Weather
import com.example.myapplication.presentation.recyclerview.City
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class FindCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    @Named("ioCoroutineContext")
    private val context: CoroutineContext
) {
    suspend fun getWeatherByCityName(cityName: String): Weather? =
        withContext(context) {
            weatherRepository.getWeatherByCityName(cityName)
        }

    suspend fun getWeatherById(id: Int): Weather? =
        withContext(context) {
            weatherRepository.getWeatherById(id)
        }

    suspend fun getWeatherByGeo(
        latitude: Double,
        longitude: Double,
        count: Int
    ): List<City> =
        withContext(context) {
            weatherRepository.getWeatherByGeo(latitude, longitude, count)
        }
}
