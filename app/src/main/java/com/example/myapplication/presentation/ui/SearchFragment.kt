package com.example.myapplication.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.R
import com.example.myapplication.data.repositories.LocationRepositoryImpl
import com.example.myapplication.data.repositories.WeatherRepositoryImpl
import com.example.myapplication.data.retrofit.ApiFactory
import com.example.myapplication.data.room.WeatherDatabase
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.domain.FindCityUseCase
import com.example.myapplication.domain.LocationUseCase
import com.example.myapplication.presentation.recyclerview.City
import com.example.myapplication.presentation.recyclerview.CityAdapter
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class SearchFragment : Fragment(), SearchView {

//    private lateinit var searchPresenter: SearchPresenter
    private lateinit var findCityUseCase: FindCityUseCase
    private var _binding: FragmentSearchBinding? = null
    private val binding get(): FragmentSearchBinding = _binding!!
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var locationUseCase: LocationUseCase
    private lateinit var adapter: CityAdapter
    private lateinit var startingCityFragment: StartingCityFragment

    interface StartingCityFragment {
        fun start(cityId: Int)
    }

    companion object {
        private const val PERMISSION_GEO = 999
        const val CITY_ID = "CITY_ID_KEY"

        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startingCityFragment = context as StartingCityFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        findCityUseCase = FindCityUseCase(WeatherRepositoryImpl(ApiFactory.weatherApi,
            WeatherDatabase.getInstance(requireContext()).weatherDAO),  Dispatchers.IO)
        locationUseCase = LocationUseCase(LocationRepositoryImpl(LocationServices.getFusedLocationProviderClient(requireContext().applicationContext)), Dispatchers.IO )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter = CityAdapter {
            startDetailInformationFragment(it)
            //TODO()
            //searchPresenter.clickOnRecyclerItem(it)
        }
        with(binding){
            rvNearestCities.adapter = adapter
            rvNearestCities.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            swipeRefreshLayout.setOnRefreshListener{
                requestPermissions()
                swipeRefreshLayout.isRefreshing = false
                //TODO()
            }
        }
        requestPermissions()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        searchView = menu.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView
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
                showCitiesWeatherByDefault()
            }
        }
    }
//
//    fun initPresenter(){
//        searchPresenter = SearchPresenter(this, FindCityUseCase(WeatherRepositoryImpl(ApiFactory.weatherApi,
//            WeatherDatabase.getInstance(requireContext()).weatherDAO),  Dispatchers.IO))
//    }

    private fun findCityByName(name: String) {
        lifecycleScope.launch {
            try {
                val weather = findCityUseCase.getWeatherByCityName(name)
                weather?.let {
                    startDetailInformationFragment(it.id)
                }
                if(weather == null){
                    showSnackBar("Город не найден")
                }
            } catch (e: IOException) {
                showSnackBar("Нет интернета")
            } catch (e: Exception) {
                showSnackBar("Город не найден")
            }
        }
    }

    private fun getGeo(){
        if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {

            lifecycleScope.launch {
                try{
                    locationUseCase.getUserLocation().let{
                        showCitiesWeather(it.latitude, it.longitude)
                    }
                }
                catch (e: Exception){
                    showCitiesWeatherByDefault()
                }
            }

        }
    }

    private fun showCitiesWeatherByDefault(){
        showCitiesWeather(55.7887, 49.1221)
        showSnackBar("По умолчанию выбрана Казань")
    }

    private fun showCitiesWeather(latitude: Double, longitude: Double) {
        lifecycleScope.launch {
            val list = findCityUseCase.getWeatherByGeo(latitude, longitude, 10)
            adapter.submitList(list.toMutableList())
        }
    }

    private fun startDetailInformationFragment(id: Int) {
        startingCityFragment.start(id)
    }

    override fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
        (activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                binding.root.windowToken,
                0
        )
    }

    private fun requestPermissions() {
        requestPermissions(
                arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ), PERMISSION_GEO
        )
    }

}