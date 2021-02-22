package com.example.myapplication.retrofit

import com.example.myapplication.entities.Weather
import com.example.myapplication.entities.WeatherList
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByName(
        @Query("q") cityName: String
    ): Weather

    @GET("weather")
    suspend fun getWeatherById(
        @Query("id") id: Int
    ): Weather

    @GET("find?lang=ru")
    suspend fun getWeatherByGeo(
        @Query("lat") latitude : Double, @Query("lon") longitude: Double, @Query("cnt") count: Int): WeatherList
}