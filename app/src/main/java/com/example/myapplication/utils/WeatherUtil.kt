package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.R
import dagger.hilt.android.qualifiers.ApplicationContext

class WeatherUtil {

    companion object{
        fun getColorByTemp(temperature: Double): Int {
            var color: Int = R.color.black
            if (temperature < -25) color = R.color.blue
            if (temperature >= -25 && temperature < -5) color = R.color.light_blue
            if (temperature >= -5 && temperature < 10) color = R.color.green
            if (temperature >= 10 && temperature < 25) color = R.color.yellow
            if (temperature >= 25 && temperature < 40) color = R.color.orange
            if (temperature >= 40) color = R.color.red
            return color
        }

        fun getDirectionByDegree(context: Context, deg: Int) = when (deg) {
            in 0..22 -> context.resources.getString(R.string.north)
            in 23..67 -> context.resources.getString(R.string.northeast)
            in 68..112 -> context.resources.getString(R.string.east)
            in 113..157 -> context.resources.getString(R.string.southeast)
            in 158..202 -> context.resources.getString(R.string.south)
            in 203..247 -> context.resources.getString(R.string.southwest)
            in 248..292 -> context.resources.getString(R.string.west)
            in 293..336 -> context.resources.getString(R.string.northwest)
            in 337..360 -> context.resources.getString(R.string.north)
            else -> ""
        }
    }

}
