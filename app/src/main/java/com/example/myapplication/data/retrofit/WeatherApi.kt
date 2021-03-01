package com.example.myapplication.data.retrofit

import com.example.myapplication.data.entities.WeatherResponse
import com.example.myapplication.data.entities.WeatherListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherById(
        @Query("id") id: Int
    ): WeatherResponse

    @GET("find")
    suspend fun getWeatherByGeo(
        @Query("lat") latitude : Double, @Query("lon") longitude: Double, @Query("cnt") count: Int): WeatherListResponse
}