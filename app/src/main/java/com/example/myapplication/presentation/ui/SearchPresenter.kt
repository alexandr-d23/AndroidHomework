package com.example.myapplication.presentation.ui

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.FindCityUseCase
import com.example.myapplication.domain.LocationUseCase
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.io.IOException

class SearchPresenter(
    private val findCityUseCase: FindCityUseCase,
    private val locationUseCase: LocationUseCase,
    private val context: Context
) : MvpPresenter<SearchView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkLocationPermission()
    }

    fun permissionsGranted() {
        checkLocationPermission()
    }

    fun permissionsDenied() {
        showCitiesWeatherByDefault()
    }

    fun onRecyclerItemClick(id: Int) {
        viewState.startDetailInformationFragment(id)
    }

    fun onCheckPermissionResult(result: Boolean) {
        if (result) {
            presenterScope.launch {
                try {
                    locationUseCase.getUserLocation().let {
                        showCitiesWeather(it.latitude, it.longitude)
                    }
                } catch (e: Exception) {
                    showCitiesWeatherByDefault()
                }

            }
        } else {
            viewState.requestPermissions()
        }
    }

    fun onSwipeRefreshListener() {
        checkLocationPermission()
    }

    fun searchTextSubmitted(text: String) {
        findCityByName(text)
    }

    private fun checkLocationPermission() {
        viewState.checkLocationPermission()
    }

    private fun showCitiesWeather(latitude: Double, longitude: Double) {
        presenterScope.launch {
            val list = findCityUseCase.getWeatherByGeo(latitude, longitude, 10)
            viewState.showCityList(list)
        }
    }

    private fun showCitiesWeatherByDefault() {
        showCitiesWeather(55.7887, 49.1221)
        viewState.showSnackBar(context.getString(R.string.choosed_kazan_by_defualt))
    }

    private fun findCityByName(name: String) {
        presenterScope.launch {
            try {
                val weather = findCityUseCase.getWeatherByCityName(name)
                weather?.let {
                    viewState.startDetailInformationFragment(it.id)
                } ?: viewState.showSnackBar(context.resources.getString(R.string.city_not_found))
            } catch (e: IOException) {
                viewState.showSnackBar(context.resources.getString(R.string.no_internet_connection))
            } catch (e: Exception) {
                viewState.showSnackBar(context.resources.getString(R.string.city_not_found))
            }
        }
    }
}