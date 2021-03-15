package com.example.myapplication.presentation.ui

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface MainView : MvpView {
    fun popBackStack()
    fun navigateToCityFragment(cityId: Int)
}