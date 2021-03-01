package com.example.myapplication.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.repositories.WeatherRepositoryImpl
import com.example.myapplication.data.retrofit.ApiFactory
import com.example.myapplication.data.entities.Weather
import com.example.myapplication.data.room.WeatherDatabase
import com.example.myapplication.databinding.FragmentCityWeatherBinding
import com.example.myapplication.domain.FindCityUseCase
import kotlinx.coroutines.Dispatchers

class CityWeatherFragment : Fragment() {

    private var _binding: FragmentCityWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var useCase : FindCityUseCase

    companion object{
        fun newInstance(cityId: Int): CityWeatherFragment{
            val args = Bundle().apply {
                putInt(SearchFragment.CITY_ID, cityId)

            }
            val fragment = CityWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useCase = FindCityUseCase(WeatherRepositoryImpl(ApiFactory.weatherApi,WeatherDatabase.getInstance(requireContext()).weatherDAO),  Dispatchers.IO)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCityWeatherBinding.inflate(layoutInflater, container, false)
        val cityId = arguments?.getInt(SearchFragment.CITY_ID) ?: 0
        lifecycleScope.launchWhenCreated {
            useCase.getWeatherById(cityId)?.let {
                bindData(it)
            }
        }
        return binding.root
    }

    private fun bindData(weatherResponse: Weather) {
        with(binding) {
            tvName.text = weatherResponse.name
            tvTemperature.text = "${weatherResponse.main.temp.toInt()}℃"
            val color = ContextCompat.getColor(
                binding.root.context,
                getColorByTemp(weatherResponse.main.temp)
            )
            mainCard.setCardBackgroundColor(color)
            tvDescription.text = weatherResponse.innerWeather.description
            tvWindSpeed.text = "${weatherResponse.wind.speed}м/с"
            tvHumidity.text = "${weatherResponse.main.humidity}%"
            tvWindDirection.text = getDirectionByDegree(weatherResponse.wind.deg)
            tvFeelsLike.text = "${weatherResponse.main.feelsLike.toInt()}℃"
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun getDirectionByDegree(deg: Int) = when (deg) {
        in 0..22 -> "Северный"
        in 23..67 -> "Северо-восточный"
        in 68..112 -> "Восточный"
        in 113..157 -> "Юго-восточный"
        in 158..202 -> "Южный"
        in 203..247 -> "Юго-западный"
        in 248..292 -> "Западный"
        in 293..336 -> "Северо-западный"
        in 337..360 -> "Северный"
        else -> "нет такого"
    }

    private fun getColorByTemp(temperature: Double): Int {
        var color: Int = R.color.black
        if (temperature < -25) color = R.color.blue
        if (temperature >= -25 && temperature < -5) color = R.color.light_blue
        if (temperature >= -5 && temperature < 10) color = R.color.green
        if (temperature >= 10 && temperature < 25) color = R.color.yellow
        if (temperature >= 25 && temperature < 40) color = R.color.orange
        if (temperature >= 40) color = R.color.red
        return color
    }

}