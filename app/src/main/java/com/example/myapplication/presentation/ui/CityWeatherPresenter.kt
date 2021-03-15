package com.example.myapplication.presentation.ui

import androidx.lifecycle.lifecycleScope
import com.example.myapplication.domain.FindCityUseCase
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class CityWeatherPresenter(
    private val useCase: FindCityUseCase
) : MvpPresenter<CityWeatherView>() {
    fun onLoaded(cityId: Int){
        presenterScope.launch{
            useCase.getWeatherById(cityId)?.let {
                viewState.updateWeather(it)
            }
        }
    }
}