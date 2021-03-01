package com.example.myapplication.data.repositories

import com.example.myapplication.data.retrofit.WeatherApi
import com.example.myapplication.data.room.WeatherDAO
import com.example.myapplication.data.entities.Weather
import com.example.myapplication.data.entities.WeatherNearestCity
import com.example.myapplication.presentation.recyclerview.City
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val weatherDAO: WeatherDAO
) : WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): Weather? {
        return try {
            val weatherResponse = weatherApi.getWeatherByCityName(cityName)
            Weather(
                weatherResponse.id,
                weatherResponse.main,
                weatherResponse.name,
                weatherResponse.innerWeather[0],
                weatherResponse.wind
            ).also {
                weatherDAO.insertWeather(it)
            }
        } catch (e: IOException) {
            weatherDAO.getWeatherByName(cityName).getOrNull(0)
        }
    }

    override suspend fun getWeatherById(id: Int): Weather? {
        return try {
            val weatherResponse = weatherApi.getWeatherById(id)
            Weather(
                weatherResponse.id,
                weatherResponse.main,
                weatherResponse.name,
                weatherResponse.innerWeather[0],
                weatherResponse.wind
            ).also {
                weatherDAO.insertWeather(it)
            }
        } catch (e: IOException) {
            weatherDAO.getWeatherById(id).getOrNull(0)
        }
    }

    override suspend fun getWeatherByGeo(
        latitude: Double,
        longitude: Double,
        count: Int
    ): List<City> {
        return try {
            weatherApi.getWeatherByGeo(latitude, longitude, count).list.onEach {
                weatherDAO.insertWeather(
                    Weather(
                        it.id,
                        it.main,
                        it.name,
                        it.innerWeather[0],
                        it.wind
                    )
                )
                weatherDAO.insertNearestCity(
                    WeatherNearestCity(
                        city = City(
                            it.id,
                            it.name,
                            it.main.temp
                        )
                    )
                )
            }.map { weatherResponse ->
                City(
                    weatherResponse.id,
                    weatherResponse.name,
                    weatherResponse.main.temp
                )
            }
        } catch (e: Exception) {
            weatherDAO.getNearestCities().map {
                it.city
            }
        }
    }
}