package com.example.myapplication.presentation.ui

import com.example.myapplication.data.entities.Weather
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface CityWeatherView : MvpView {
    fun updateWeather(weather: Weather)
}
