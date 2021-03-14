package com.example.myapplication.data.entities

import com.google.gson.annotations.SerializedName

data class WeatherListResponse(
    @SerializedName("count")
    var count: Int,
    @SerializedName("list")
    var list: List<WeatherResponse>,
    @SerializedName("message")
    var message: String
)
