package com.example.myapplication.presentation.ui

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(), SearchFragment.StartingCityFragment, MainView {

    private lateinit var binding: ActivityMainBinding

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = MainPresenter()

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
        presenter.backPressed()
    }

    override fun userSearchedCity(cityId: Int) {
        presenter.userSearchedCity(cityId)
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun navigateToCityFragment(cityId: Int) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fl_container,
            CityWeatherFragment.newInstance(cityId)
        ).addToBackStack(null).commit()
    }

}
