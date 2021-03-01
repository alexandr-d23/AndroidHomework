package com.example.myapplication.data.entities


import androidx.room.*
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        @SerializedName("id")
        var id: Int,
        @SerializedName("main")
        var main: Main,
        @SerializedName("name")
        var name: String,
        @SerializedName("weather")
        var innerWeather: List<InnerWeather>,
        @SerializedName("wind")
        var wind: Wind
) {

    data class Main(
            @SerializedName("feels_like")
            var feelsLike: Double,
            @SerializedName("humidity")
            var humidity: Int,
            @SerializedName("pressure")
            var pressure: Int,
            @SerializedName("temp")
            var temp: Double,
            @SerializedName("temp_max")
            var tempMax: Double,
            @SerializedName("temp_min")
            var tempMin: Double
    )

    data class InnerWeather(
            @SerializedName("description")
            var description: String,
            @SerializedName("icon")
            var icon: String,
            @SerializedName("id")
            var id: Int,
            @SerializedName("main")
            var main: String
    )

    data class Wind(
            @SerializedName("deg")
            var deg: Int,
            @SerializedName("speed")
            var speed: Double
    )
}