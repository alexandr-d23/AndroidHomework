package com.example.myapplication.presentation.ui

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(), SearchFragment.StartingCityFragment {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(
            R.id.fl_container,
            SearchFragment.newInstance()
        ).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

    override fun startCityFragment(cityId: Int) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fl_container,
            CityWeatherFragment.newInstance(cityId)
        ).addToBackStack(null).commit()
    }

}
