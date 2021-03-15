package com.example.myapplication.presentation.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import moxy.MvpFacade

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
    }
}
