package com.example.myapplication.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.entities.Weather
import com.example.myapplication.databinding.FragmentCityWeatherBinding
import com.example.myapplication.domain.FindCityUseCase
import com.example.myapplication.utils.WeatherUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CityWeatherFragment : Fragment() {

    private var _binding: FragmentCityWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var useCase: FindCityUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            tvTemperature.text =
                "${weatherResponse.main.temp.toInt()}${resources.getString(R.string.temperature_suffix)}"
            val color = ContextCompat.getColor(
                binding.root.context,
                WeatherUtil.getColorByTemp(weatherResponse.main.temp)
            )
            mainCard.setCardBackgroundColor(color)
            tvDescription.text = weatherResponse.innerWeather.description
            tvWindSpeed.text = "${weatherResponse.wind.speed}"
            tvHumidity.text =
                "${weatherResponse.main.humidity}${resources.getString(R.string.wind_speed_suffix)}"
            tvWindDirection.text = WeatherUtil.getDirectionByDegree(
                requireContext().applicationContext,
                weatherResponse.wind.deg
            )
            tvFeelsLike.text =
                "${weatherResponse.main.feelsLike.toInt()}${resources.getString(R.string.wind_speed_suffix)}"
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(cityId: Int): CityWeatherFragment {
            val args = Bundle().apply {
                putInt(SearchFragment.CITY_ID, cityId)
            }
            val fragment = CityWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
