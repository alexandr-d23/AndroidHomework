package com.example.myapplication.presentation.ui

import moxy.MvpPresenter

class MainPresenter : MvpPresenter<MainView>() {

    fun backPressed(){
        viewState.popBackStack()
    }

    fun userSearchedCity(cityId: Int){
        viewState.navigateToCityFragment(cityId)
    }
}