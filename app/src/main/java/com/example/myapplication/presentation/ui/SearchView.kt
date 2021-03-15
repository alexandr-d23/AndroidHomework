package com.example.myapplication.presentation.ui

import com.example.myapplication.presentation.recyclerview.City
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface SearchView : MvpView{

    @Skip
    fun showSnackBar(text: String)

    fun showCityList(list: List<City>)

    fun checkLocationPermission()

    @Skip
    fun startDetailInformationFragment(id: Int)

    fun requestPermissions()
}
