package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCityWeatherBinding
import com.example.myapplication.entities.Weather
import com.example.myapplication.retrofit.ApiFactory

class CityWeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(MainActivity.CITY_ID, 802)
        lifecycleScope.launchWhenCreated {
            val weather = ApiFactory.weatherApi.getWeatherById(id)
            bindData(weather)
        }
    }

    private fun bindData(weather: Weather) {
        with(binding) {
            tvName.text = weather.name
            tvTemperature.text = "${weather.main.temp.toInt()}℃"
            val color = ContextCompat.getColor(
                binding.root.context,
                getColorByTemp(weather.main.temp)
            )
            mainCard.setCardBackgroundColor(color)
            tvDescription.text = weather.weather[0].description
            tvWindSpeed.text = "${weather.wind.speed}м/с"
            tvHumidity.text = "${weather.main.humidity}%"
            tvWindDirection.text = getDirectionByDegree(weather.wind.deg)
            tvFeelsLike.text = "${weather.main.feelsLike.toInt()}℃"
            btnBack.setOnClickListener {
                onBackPressed()
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