package com.example.myapplication.ui

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
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.entities.City
import com.example.myapplication.recyclerview.CityAdapter
import com.example.myapplication.retrofit.ApiFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val api = ApiFactory.weatherApi
    private lateinit var adapter: CityAdapter

    companion object {
        private const val PERMISSION_GEO = 999
        const val CITY_ID = "CITY_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        adapter = CityAdapter {
            startDetailInformationActivity(it)
        }
        with(binding){
            rvNearestCities.adapter = adapter
            rvNearestCities.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            swipeRefreshLayout.setOnRefreshListener{
                requestPermissions()
                swipeRefreshLayout.isRefreshing = false
            }
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchView =
            menu?.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                findCityByName(p0 ?: "")
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_GEO) {
            var areGranted = true
            grantResults.forEach {
                if (it == PackageManager.PERMISSION_DENIED) {
                    areGranted = false
                }
            }
            if (areGranted) {
                Log.d("MYTAG", "Получены пермишены")
                getGeo()
            } else {
                Log.d("MYTAG", "Не получены пермишены")
                showCitiesWeather(55.7887, 49.1221)
                showSnackBar("По умолчанию выбрана Казань")
            }
        }
    }

    private fun findCityByName(name: String) {
        lifecycleScope.launch {
            try {
                val weather = api.getWeatherByName(name)
                startDetailInformationActivity(weather.id)
            } catch (e: IOException) {
                showSnackBar("Нет интернета")
            } catch (e: HttpException) {
                showSnackBar("Город не найден")
            }
        }
    }

    private fun getGeo() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    showCitiesWeather(it.result.latitude, it.result.longitude)
                } else {
                    showCitiesWeather(55.7887, 49.1221)
                    showSnackBar("По умолчанию выбрана Казань")
                }
            }
        }
    }


    private fun showCitiesWeather(latitude: Double, longitude: Double) {
        lifecycleScope.launch {
            val list = ApiFactory.weatherApi.getWeatherByGeo(latitude, longitude, 10).list
            adapter.submitList(list.map { weather ->
                City(weather.id, weather.name, weather.main.temp)
            }.toMutableList())
        }
    }

    private fun startDetailInformationActivity(id: Int) {
        val intent = Intent(this@MainActivity, CityWeatherActivity::class.java)
            .apply {
                putExtra(CITY_ID, id)
            }
        startActivity(intent)
    }


    private fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            binding.root.windowToken,
            0
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_GEO
        )
    }


}