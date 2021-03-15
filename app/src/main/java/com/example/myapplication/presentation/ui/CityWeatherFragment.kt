package com.example.myapplication.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.entities.Weather
import com.example.myapplication.databinding.FragmentCityWeatherBinding
import com.example.myapplication.domain.FindCityUseCase
import com.example.myapplication.utils.WeatherUtil
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class CityWeatherFragment : MvpAppCompatFragment(), CityWeatherView {

    private var _binding: FragmentCityWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var useCase: FindCityUseCase

    @InjectPresenter
    lateinit var presenter: CityWeatherPresenter

    @ProvidePresenter
    fun providePresenter() = CityWeatherPresenter(useCase)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityWeatherBinding.inflate(layoutInflater, container, false)
        val cityId = arguments?.getInt(SearchFragment.CITY_ID) ?: 0
        presenter.onLoaded(cityId)
        return binding.root
    }

    private fun bindData(weather: Weather) {
        with(binding) {
            tvName.text = weather.name
            tvTemperature.text =
                "${weather.main.temp.toInt()}${resources.getString(R.string.temperature_suffix)}"
            val color = ContextCompat.getColor(
                binding.root.context,
                WeatherUtil.getColorByTemp(weather.main.temp)
            )
            mainCard.setCardBackgroundColor(color)
            tvDescription.text = weather.innerWeather.description
            tvWindSpeed.text = "${weather.wind.speed}"
            tvHumidity.text =
                "${weather.main.humidity}${resources.getString(R.string.wind_speed_suffix)}"
            tvWindDirection.text = WeatherUtil.getDirectionByDegree(
                requireContext().applicationContext,
                weather.wind.deg
            )
            tvFeelsLike.text =
                "${weather.main.feelsLike.toInt()}${resources.getString(R.string.wind_speed_suffix)}"
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

    override fun updateWeather(weather: Weather) {
        bindData(weather)
    }
}
