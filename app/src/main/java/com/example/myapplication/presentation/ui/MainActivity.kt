package com.example.myapplication.presentation.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.R
import com.example.myapplication.data.entities.*
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.recyclerview.City
import com.example.myapplication.presentation.recyclerview.CityAdapter
import com.example.myapplication.data.retrofit.ApiFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity(), SearchFragment.StartingCityFragment {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, SearchFragment.newInstance()).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

    override fun start(cityId: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, CityWeatherFragment.newInstance(cityId)).addToBackStack(null).commit()
    }

}