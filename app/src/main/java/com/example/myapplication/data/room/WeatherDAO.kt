package com.example.myapplication.data.room

import androidx.room.*
import com.example.myapplication.data.entities.Weather
import com.example.myapplication.data.entities.WeatherNearestCity

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNearestCity(weatherNearestCity: WeatherNearestCity)

    @Query("SELECT * FROM city ORDER BY id DESC LIMIT 10 ")
    suspend fun getNearestCities(): List<WeatherNearestCity>

    @Query("SELECT * FROM Weather WHERE id = :id")
    suspend fun getWeatherById(id: Int): List<Weather>

    @Query("SELECT * FROM Weather WHERE name = :cityName")
    suspend fun getWeatherByName(cityName: String): List<Weather>

}
